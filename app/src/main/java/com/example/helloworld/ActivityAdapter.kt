package com.example.helloworld

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private val onItemClick: (Activity) -> Unit
) : ListAdapter<Any, RecyclerView.ViewHolder>(ActivityDiffCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ACTIVITY = 1
    }

    fun submitActivities(activities: List<Activity>) {
        val items = mutableListOf<Any>()

        activities.groupBy { it.date }.forEach { (date, activitiesForDate) ->
            items.add(date)
            items.addAll(activitiesForDate)
        }

        submitList(items)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is String -> TYPE_HEADER
            is Activity -> TYPE_ACTIVITY
            else -> throw IllegalArgumentException("Invalid type at position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_item_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ACTIVITY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_item_main, parent, false)
                ActivityViewHolder(view, onItemClick)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(getItem(position) as String)
            is ActivityViewHolder -> holder.bind(getItem(position) as Activity)
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateText: TextView = view.findViewById(R.id.dateText)

        fun bind(date: String) {
            dateText.text = date
        }
    }

    class ActivityViewHolder(
        view: View,
        private val onItemClick: (Activity) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val distanceText: TextView = view.findViewById(R.id.distanceText)
        private val durationText: TextView = view.findViewById(R.id.durationText)
        private val timeAgoText: TextView = view.findViewById(R.id.timeAgoText)
        private val typeText: TextView = view.findViewById(R.id.typeText)
        private val usernameText: TextView = view.findViewById(R.id.usernameText)

        fun bind(activity: Activity) {
            distanceText.text = activity.distance
            durationText.text = activity.duration
            timeAgoText.text = if (activity.timeAgo.isBlank()) "Сейчас" else activity.timeAgo
            typeText.text = activity.type

            if (activity.isFromOtherUser == true && !activity.user.isNullOrEmpty()) {
                usernameText.visibility = View.VISIBLE
                usernameText.text = activity.user
            } else {
                usernameText.visibility = View.GONE
            }

            itemView.setOnClickListener { onItemClick(activity) }
        }
    }
}

class ActivityDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return when {
            oldItem is String && newItem is String -> oldItem == newItem
            oldItem is Activity && newItem is Activity -> oldItem.id == newItem.id
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }
}