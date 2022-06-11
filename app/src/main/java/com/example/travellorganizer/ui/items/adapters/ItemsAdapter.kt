package com.example.travellorganizer.ui.items.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travellorganizer.R
import com.example.travellorganizer.models.Items
import kotlinx.coroutines.NonDisposableHandle.parent

class ItemsAdapter (val items: ArrayList<Items>) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>(){

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)


        return ItemsViewHolder(view.inflate(R.layout.item, viewGroup, false))
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemsViewHolder, position: Int) {

        val item = items[position]

        viewHolder.item.text = item.name
    }

    override fun getItemCount() = items.size



    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val item = itemView.findViewById<TextView>(R.id.item)
    }
}