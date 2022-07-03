package com.example.travelorganizer.ui.lists.adapters

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.ui.items.ItemsFragment
import com.example.travelorganizer.ui.items.adapters.ItemsAdapter
import com.example.travelorganizer.ui.lists.ItemToListFragment

class ItemToListAdapter(val fragment : ItemToListFragment) : RecyclerView.Adapter<ItemToListAdapter.ItemsToListViewHolder>() {
    var cursor: Cursor? = null
        get() = field
        set(value){
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemsToListViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)

        return ItemsToListViewHolder(view.inflate(R.layout.list, viewGroup, false))
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ItemsToListViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        viewHolder.items = Items.fromCursor(cursor!!)
    }

    override fun getItemCount() : Int {
        if (cursor == null) return 0

        return cursor!!.count

    }



    inner class ItemsToListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val itemViewText = itemView.findViewById<TextView>(R.id.listCardTextView)

        var items : Items? = null
            get() = field
            set(value){
                field = value

                itemViewText.text =items?.name ?:" "
            }

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            isChecked()
        }

        fun isChecked(){
            checked = this

            fragment.ids!!.forEach {
                if(it != items?.id){
                    fragment.ids?.add(items?.id)

                }
            }
            itemView.setBackgroundResource(R.color.white_grey)
        }
    }

    companion object{
        var checked : ItemsToListViewHolder? = null
    }

}