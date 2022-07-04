package com.example.travelorganizer.ui.lists.adapters

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Items
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListsBodyViewHolder {
        val itemList= fragment.layoutInflater.inflate(R.layout.item, parent, false)

        return ListsBodyViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: ListsBodyViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        holder.items = Items.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }

    inner class ListsBodyViewHolder(listBodyView: View) : RecyclerView.ViewHolder(listBodyView), View.OnClickListener {

        val listBodyViewText = listBodyView.findViewById<TextView>(R.id.itemCardTextView)
        val listBodyCheckbox = listBodyView.findViewById<CheckBox>(R.id.itemCardCheckBox)
        var isChecked = false
        var items : Items? = null
            get() = field
            set(value){
                field = value

                listBodyViewText.text =items?.name ?:" "
                if( items?.listItem?.status == 1){
                    listBodyCheckbox.isChecked = true

                }

                listBodyCheckbox.setOnClickListener{
                    isChecked()
                }
            }



        init{


            listBodyView.setOnClickListener(this)

        }


        override fun onClick(p0: View?) {
            isChecked()

        }
        // função que retorna o livro selecionado para o fragment, muda a cor ao clicar
        private fun isChecked(){
            if(!isChecked){

                listBodyCheckbox.isChecked = !listBodyCheckbox.isChecked

                itemView.setBackgroundResource(R.color.white_grey)
                fragment.updateCheckedItem(items, 1 )
                isChecked = true


                fragment.ids!!.add(items?.id)
            }else{
                listBodyCheckbox.isChecked = false
                itemView.setBackgroundResource(R.color.layout_grey)
                fragment.updateCheckedItem(items, 0 )
                isChecked = false


            }
        }





        //função que deseleciona o item
        private fun uncheck(){
            itemView.setBackgroundResource(R.color.layout_grey)

        }
    }
}