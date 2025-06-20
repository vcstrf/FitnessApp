package com.example.helloworld
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TypeAdapter (
    items: List<TypeItem>,
    private val onItemClick: (TypeItem) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_DEFAULT = 0
        private const val TYPE_SELECTED = 1
    }

    private val mutableItems = items.toMutableList()

    override fun getItemViewType(position: Int): Int {
        return if (mutableItems[position].isSelected) TYPE_SELECTED
        else TYPE_DEFAULT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DEFAULT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.type_item, parent, false)
                DefaultViewHolder(view)
            }
            TYPE_SELECTED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.type_item_selected, parent, false)
                SelectedViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mutableItems[position]
        when (holder) {
            is SelectedViewHolder -> holder.bind(item)
            is DefaultViewHolder -> holder.bind(item)
        }

        holder.itemView.setOnClickListener {
            mutableItems.forEach { it.isSelected = false }
            mutableItems[position].isSelected = true
            notifyDataSetChanged()

            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = mutableItems.size

    class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TypeItem) {
            val textView = itemView.findViewById<TextView>(R.id.typeText)
            textView.text = item.name
        }
    }

    class SelectedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TypeItem) {
            val textView = itemView.findViewById<TextView>(R.id.typeText)
            textView.text = item.name
        }
    }
}