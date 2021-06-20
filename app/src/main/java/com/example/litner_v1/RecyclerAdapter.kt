package com.example.litner_v1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val items:ArrayList<Users>,val context: Context):RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.title.text = items.get(position).firstName
        holder.subtitle.text = items.get(position).lastName
        holder.id.text = items.get(position).id.toString()
        holder.ranknumber1.text = items.get(position).rankNumber.toString()
        holder.timestamp1.text = items.get(position).timeStamp.toString()
        holder.readycard.text = items.get(position).readyCarD.toString()



    }

    override fun getItemCount(): Int {
        return items.size
    }
}




class ViewHolder(view:View): RecyclerView.ViewHolder(view){

    val title:TextView = view.findViewById(R.id.title_id)
    val subtitle:TextView = view.findViewById(R.id.subtitle_id)
    val id:TextView = view.findViewById(R.id.txtid_id)
    val ranknumber1:TextView = view.findViewById(R.id.txtrank_id)
    val timestamp1:TextView = view.findViewById(R.id.txttime_id)
    val readycard:TextView = view.findViewById(R.id.ready_id)





}