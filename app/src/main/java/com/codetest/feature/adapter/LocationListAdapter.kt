package com.codetest.feature.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetest.feature.model.Location

class LocationListAdapter(private val locations: ArrayList<Location>, private val listener: LocationListListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface LocationListListener{
        fun onItemDeleteClicked(location: Location)
    }
    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LocationViewHolder.create(parent)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as? LocationViewHolder)?.setup(locations[position],listener)
    }

    fun  addItems(list: List<Location>) {
        locations.clear()
        locations.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(location: Location){
        locations.removeAll { it.id != null && it.id == location.id }
        notifyDataSetChanged()
    }
}