package com.example.travelorganizer.ui.lists.adapters

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.ListBodyFragment
import com.example.travelorganizer.ui.lists.ListsFragmentDirections

class ListBodyAdapter(val fragment : ListBodyFragment) :  RecyclerView.Adapter<ListBodyAdapter.ListsBodyViewHolder>() {

    //cursor que vai passar para o fragment no loader
    var cursor: Cursor? = null
        get() = field
        set(value){
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    inner class ListsBodyViewHolder(listBodyView: View) : RecyclerView.ViewHolder(listBodyView), View.OnClickListener {

        val listBodyViewText = listBodyView.findViewById<TextView>(R.id.listCardTextView)

        init{
            listBodyView.setOnClickListener(this)

        }

        var list : Lists? = null
            get() = field
            set(value){
                field = value

                listBodyViewText.text =list?.name ?:" "
            }

        override fun onClick(p0: View?) {



        }
        // função que retorna o livro selecionado para o fragment, muida a cor ao clicar
        private fun isChecked(){
            fragment.findNavController().navigate(ListsFragmentDirections.actionNavigationListToNavigationListBodyFragment(list!!.id, list!!.name))
        }



        //função que deseleciona o item
        private fun uncheck(){
            itemView.setBackgroundResource(R.color.layout_grey)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsBodyViewHolder {
        val itemList= fragment.layoutInflater.inflate(R.layout.list, parent, false)

        return ListsBodyViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: ListsBodyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}