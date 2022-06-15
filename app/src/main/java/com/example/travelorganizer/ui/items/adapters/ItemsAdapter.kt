package com.example.travelorganizer.ui.items.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Items

class ItemsAdapter (val items: ArrayList<Items>) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>(){
    var selectedItem = -1
    var lastSelectedItem = -1
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

        val item = itemView.findViewById<TextView>(R.id.itemCardTextView)
        val checkBox = itemView.findViewById<CheckBox>(R.id.itemCardCheckBox)

        init{
            val arrayTest : ArrayList<String?> = ArrayList()
            val itemChecked = checkBox.isChecked

            var stringtest = ""

            itemView.setOnClickListener{

                val position = absoluteAdapterPosition

                if(itemChecked){
                    arrayTest.add(items[position].name)
                }

                for(s in arrayTest) Log.d(s!!, "teste")

            }



        }

    }
}