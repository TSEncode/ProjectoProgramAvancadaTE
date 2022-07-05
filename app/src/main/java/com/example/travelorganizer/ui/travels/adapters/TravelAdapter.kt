package com.example.travelorganizer.ui.travels.adapters

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelorganizer.R
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.Travel
import com.example.travelorganizer.ui.travels.TravelsFragment
import com.example.travelorganizer.ui.travels.TravelsFragmentDirections

class TravelAdapter(val fragment : TravelsFragment) : RecyclerView.Adapter<TravelAdapter.TravelsViewHolder>()  {

    var cursor: Cursor? = null
        get() = field
        set(value){
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TravelsViewHolder {
        val view = LayoutInflater.from(parent.context)

        return TravelsViewHolder(view.inflate(R.layout.list, parent, false))

    }

    override fun onBindViewHolder(holder: TravelsViewHolder, position: Int) {
        cursor!!.moveToPosition(position)
        holder.travel = Travel.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }

    inner class TravelsViewHolder(travelView: View) : RecyclerView.ViewHolder(travelView), View.OnClickListener {

        val itemViewText = travelView.findViewById<TextView>(R.id.listCardTextView)

        var travel : Travel? = null
            get() = field
            set(value){
                field = value

                itemViewText.text = travel?.name ?:" "
            }

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val action = TravelsFragmentDirections.actionNavigationTravelToTravelBodyFragment(travel!!)
            fragment.findNavController().navigate(action)
        }


    }


}