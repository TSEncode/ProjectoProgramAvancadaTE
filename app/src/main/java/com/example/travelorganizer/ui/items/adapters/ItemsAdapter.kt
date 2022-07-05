package com.example.travelorganizer.ui.items.adapters

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.items.ItemsFragment
import org.w3c.dom.Text

class ItemsAdapter (val fragment : ItemsFragment) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>(){
    var cursor: Cursor? = null
        get() = field
        set(value){
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)

        return ItemsViewHolder(view.inflate(R.layout.list, viewGroup, false))
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemsViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        viewHolder.items = Items.fromCursor(cursor!!)

    }

    override fun getItemCount() : Int {
        if (cursor == null) return 0

        return cursor!!.count

    }


    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val itemViewText = itemView.findViewById<TextView>(R.id.listCardTextView)

        var items : Items? = null
            get() = field
            set(value){
                field = value

                itemViewText.text =items?.name ?:" "
            }
        var isChecked = false
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            isChecked()
        }

        fun isChecked(){
            if(!isChecked){
                fragment.items = items
                itemView.setBackgroundResource(R.color.white_grey)

                isChecked = true
            }else{
                fragment.items = null

                itemView.setBackgroundResource(R.color.layout_grey)
                isChecked = false

            }

        }
    }

}