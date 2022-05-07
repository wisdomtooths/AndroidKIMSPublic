package com.example.kimshealth.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.LayTimeSlotBinding
import com.example.kimshealth.model.timeslots.ResponseTimeSlot
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import kotlinx.coroutines.NonDisposableHandle.parent

class AdpaterTimeSlot(val responseTimeSlot: ResponseTimeSlot,var mContext:Context): RecyclerView.Adapter<AdpaterTimeSlot.AdpaterTimeSlotHolder>() {

    lateinit var binding: LayTimeSlotBinding
    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdpaterTimeSlotHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.lay_time_slot,
            parent,false)

        return AdpaterTimeSlot.AdpaterTimeSlotHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdpaterTimeSlotHolder, position: Int) {
        binding.btntime.text = responseTimeSlot.data[position].ALLOCATION_TIME

        if(selectedPosition==position){
            holder.itemView.setBackgroundColor(Color.parseColor("#C52029"))
            var a=holder.itemView as TextView
            a.setTextColor(Color.parseColor("#ffffff"))
        }
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        binding.btntime.setOnClickListener {
            selectedPosition=position
            notifyDataSetChanged()

            //      binding.btntime.setBackgroundColor(binding.btntime.context.getResources().getColor(R.color.colorMain))

          //  Toast.makeText(binding.btntime.context,"Time Slot Selected:"+binding.btntime.text,Toast.LENGTH_LONG).show()
            (this.mContext as BookAppointmentDrProfile).seletedTimeslot(responseTimeSlot.data[position].ALLOCATION_TIME)
//            if(status){
//              //  binding.btntime.setBackgroundColor(binding.btntime.context.getResources().getColor(R.color.white))
//                status=false
//                (this.mContext as BookAppointmentDrProfile).seletedTimeslot("00")
//            }
//            else{
//               //binding.btntime.setBackgroundColor(binding.btntime.context.getResources().getColor(R.color.colorMain))
//
//                status=true
//            }
            AdpaterTimeSlotHolder(it).buttonClick(it)
        }

      //  setAnimation(holder.itemView, position);
    }



    override fun getItemCount(): Int {
        return responseTimeSlot.data.size
    }


    class  AdpaterTimeSlotHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun buttonClick(itemView: View){
            var button: Button =itemView as Button
            button.setOnClickListener {
              //  button.setBackgroundColor(itemView.context.getResources().getColor(R.color.colorMain))
             //   Toast.makeText(itemView.context,"Demo",Toast.LENGTH_LONG).show()
            }
        }
    }
}