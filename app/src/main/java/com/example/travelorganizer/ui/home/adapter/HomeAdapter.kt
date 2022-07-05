package com.example.travelorganizer.ui.home.adapter

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Travel
import com.example.travelorganizer.ui.home.HomeFragment
import com.example.travelorganizer.ui.home.HomeFragmentDirections

class HomeAdapter (val fragment : HomeFragment) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var cursor: Cursor? = null
        get() = field
        set(value){
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var travelItem = itemView.findViewById<TextView>(R.id.listCardTextView)
        var travels : Travel? = null
        get() = field
        set(value){
            field = value
            travelItem.text = travels?.name ?: " "
        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val action = HomeFragmentDirections.actionNavigationHomeToTravelBodyFragment(travels!!)
            fragment.findNavController().navigate(action)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)

        return HomeViewHolder(view.inflate(R.layout.list, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        holder.travels = Travel.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }


}