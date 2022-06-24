package com.example.travelorganizer.ui.lists.adapters

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.GetAdapterData
import com.example.travelorganizer.ui.lists.ListsFragment

class ListAdapter (val fragment: ListsFragment) : RecyclerView.Adapter<ListAdapter.ListsViewHolder>(){
    var cursor: Cursor? = null
    get() = field
    set(value){
        if (field != value) {
            field = value
            notifyDataSetChanged()
        }
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListsViewHolder {
        // Create a new view, which defines the UI of the list item
        //val view = LayoutInflater.from(viewGroup.context)

        val itemList= fragment.layoutInflater.inflate(R.layout.list, viewGroup, false)

        return ListsViewHolder(itemList)
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ListsViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        viewHolder.list = Lists.fromCursor(cursor!!)

    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }



    inner class ListsViewHolder(listView: View) : RecyclerView.ViewHolder(listView), View.OnClickListener {

        val listViewText = listView.findViewById<TextView>(R.id.listCardTextView)

        init{
            listView.setOnClickListener(this)

        }

        var list : Lists? = null
            get() = field
            set(value){
               field = value

                listViewText.text =list?.name ?:" "
            }

        override fun onClick(p0: View?) {
                val position = bindingAdapterPosition
        }

    }
}