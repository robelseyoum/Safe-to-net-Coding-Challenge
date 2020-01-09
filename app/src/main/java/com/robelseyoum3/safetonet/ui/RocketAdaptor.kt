package com.robelseyoum3.safetonet.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robelseyoum3.safetonet.R
import com.robelseyoum3.safetonet.model.Rockets
import kotlinx.android.synthetic.main.rockets_rows.view.*

class RocketAdaptor(private val allRocket: List<Rockets>) : RecyclerView.Adapter<RocketAdaptor.RocketViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        return RocketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rockets_rows, parent, false))
    }

    override fun getItemCount(): Int {
        return allRocket.size
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {

        holder.rocketName.text = allRocket[position].rocketName
        holder.rocketCountry.text = allRocket[position].country
        holder.rocketEngineCount.text = allRocket[position].engines.number.toString() //not sure with the request data

    }


    class RocketViewHolder (view: View): RecyclerView.ViewHolder(view) {

        val rocketName: TextView = view.tvDisplayName
        val rocketCountry: TextView = view.tvDisplayCountry
        val rocketEngineCount: TextView = view.tvEngineCountDisplay

    }

}
