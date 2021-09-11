package com.demirli.a52sportsbracketgenerator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamAdapter(var finalistList: List<List<Team>>): RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = finalistList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.finalistOne.setText(finalistList[position][0].name)
            holder.finalistTwo.setText(finalistList[position][1].name)
        }catch (e: Exception){
            e.printStackTrace()
        }


    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val finalistOne = view.findViewById<TextView>(R.id.finalist_one)
        val finalistTwo = view.findViewById<TextView>(R.id.finalist_two)
    }
}