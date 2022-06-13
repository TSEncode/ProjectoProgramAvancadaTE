package com.example.travellorganizer.ui.lists.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travellorganizer.R
import com.example.travellorganizer.models.Lists
import com.example.travellorganizer.ui.lists.ListBodyFragment
import com.example.travellorganizer.ui.lists.ListsViewModel

class ListAdapter (val lists: ArrayList<Lists>) : RecyclerView.Adapter<ListAdapter.ListsViewHolder>(){

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListsViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)


        return ListsViewHolder(view.inflate(R.layout.list, viewGroup, false))
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ListsViewHolder, position: Int) {

        val list = lists[position]

        viewHolder.listViewText.text = list.name

    }

    override fun getItemCount() = lists.size



    inner class ListsViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {

        val listViewText = listView.findViewById<TextView>(R.id.listCardTextView)


        init{
            val arrayTest : ArrayList<String?> = ArrayList()


            listView.setOnClickListener{

                val position = absoluteAdapterPosition

                val viewModel = ListsViewModel()

                viewModel.selectId(lists[position].id)

                Toast.makeText(it.context, "${lists[position].id}", Toast.LENGTH_SHORT).show()



                val args = Bundle()

                args.putLong("list_id", lists[position].id)

                val bodyFragment = ListBodyFragment()

                it.findNavController().navigate(R.id.navigation_listBodyFragment)
            }


        }

    }
}