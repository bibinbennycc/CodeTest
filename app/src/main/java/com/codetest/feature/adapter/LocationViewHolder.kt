package com.codetest.feature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.feature.model.Location
import com.codetest.feature.model.Status
import kotlinx.android.synthetic.main.location.view.*


class LocationViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.location, parent, false)
            return LocationViewHolder(view)
        }
    }

    fun setup(location: Location, listener: LocationListAdapter.LocationListListener) {
        itemView.card.setCardBackgroundColor(getColor(location.status))
        itemView.name.text = location.name
        val weather = location.temperature + "°C " + String(Character.toChars(location.status.value))
        itemView.weatherInfo.text = weather
        itemView.close_button.setOnClickListener { listener.onItemDeleteClicked(location) }
    }

    private fun getColor(status: Status): Int {
        return when (status) {
            Status.SUNNY, Status.MOSTLY_SUNNY, Status.PARTLY_SUNNY, Status.PARTLY_SUNNY_RAIN, Status.BARELY_SUNNY
            -> itemView.resources.getColor(R.color.blue)
            Status.THUNDER_CLOUD_AND_RAIN, Status.TORNADO, Status.LIGHTENING -> itemView.resources.getColor(R.color.red)
            Status.CLOUDY, Status.SNOW_CLOUD, Status.RAINY -> itemView.resources.getColor(R.color.grey)

        }
    }
}
