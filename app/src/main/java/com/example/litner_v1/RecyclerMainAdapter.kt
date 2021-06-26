package com.example.litner_v1

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

 var positionLongClick= 0
@Suppress("DEPRECATION")
class RecyclerMainAdapter(val items:ArrayList<Rec_Menu>, val context: Context):RecyclerView.Adapter<ViewHolder_RMA>() {

    var dbHandler:DatabaseHandler = DatabaseHandler(context)
    val user= Users()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_RMA {
        return ViewHolder_RMA(
            LayoutInflater.from(context).inflate(R.layout.recycler_main_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: ViewHolder_RMA, position: Int) {

        holder.step?.text = items.get(position).step
        holder.readyCards?.text = items.get(position).readyCards
        holder.capacity?.text = items.get(position).capacity
        holder.browseTime?.text = items.get(position).browseTime
        for (i in 1..position) {
            if (1 == dbHandler.getColorBackground(i)) {
                holder.itemView.setBackgroundColor(Color.RED)

            }else if (0 == dbHandler.getColorBackground(i)) {
                holder.itemView.setBackgroundColor(Color.GREEN)

            }
            else {
                holder.itemView.setBackgroundColor(Color.BLUE)
            }
        }

        //*//holder.title.setOnClickListener{Toast.makeText(context,"kkk",Toast.LENGTH_LONG).show()}

        holder.itemView.setOnClickListener{
            position1= position
            val goToMain = android.content.Intent(context, FrontPage::class.java)
           context.startActivity(goToMain)
        }
        holder.itemView.setOnLongClickListener {
            Toast.makeText(context,"looooong",Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

}
    override fun getItemCount(): Int {

        return items.size
    }

}

class ViewHolder_RMA(view:View):RecyclerView.ViewHolder(view){


    val step= view.findViewById<TextView>(R.id.rec_step_id)
    val readyCards= view.findViewById<TextView>(R.id.rec_readyCards_id)
    val capacity= view.findViewById<TextView>(R.id.rec_capacity_id)
    val browseTime= view.findViewById<TextView>(R.id.rec_browseTime_id)
    val carditem= view.findViewById<CardView>(R.id.card_id)

}

