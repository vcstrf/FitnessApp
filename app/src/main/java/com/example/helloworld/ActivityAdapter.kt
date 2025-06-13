package com.example.helloworld
import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater

class ActivityAdapter(
    items: List<ActivityItem>,
    private val onItemClick: (ActivityItem.ActivityMain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_MAIN = 1
    }

    private val mutableItems = items.toMutableList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<ActivityItem>) {
        mutableItems.clear()
        mutableItems.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (mutableItems[position]) {
            is ActivityItem.ActivityHeader -> TYPE_HEADER
            is ActivityItem.ActivityMain -> TYPE_MAIN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_item_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_MAIN -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_item_main, parent, false)
                MainViewHolder(view, onItemClick)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = mutableItems[position]) {
            is ActivityItem.ActivityHeader -> (holder as HeaderViewHolder).bind(item)
            is ActivityItem.ActivityMain -> (holder as MainViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = mutableItems.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateText: TextView = view.findViewById(R.id.dateText)

        fun bind(item: ActivityItem.ActivityHeader) {
            dateText.text = item.date
        }
    }

    class MainViewHolder(
        view: View,
        private val onItemClick: (ActivityItem.ActivityMain) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val distanceText: TextView = view.findViewById(R.id.distanceText)
        private val durationText: TextView = view.findViewById(R.id.durationText)
        private val timeAgoText: TextView = view.findViewById(R.id.timeAgoText)
        private val typeText: TextView = view.findViewById(R.id.typeText)
        private val usernameText: TextView = view.findViewById(R.id.usernameText)

        fun bind(item: ActivityItem.ActivityMain) {
            distanceText.text = item.distance
            durationText.text = item.duration
            timeAgoText.text = item.timeAgo
            typeText.text = item.type

            if (item.isFromOtherUser && !item.username.isNullOrEmpty()) {
                usernameText.visibility = View.VISIBLE
                usernameText.text = item.username
            } else {
                usernameText.visibility = View.GONE
            }

            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}