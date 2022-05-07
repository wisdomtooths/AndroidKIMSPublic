package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemAppoinmentListBinding
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import com.example.kimshealth.ui.activity.BookAppointment_cancel_reschedule

class AppointList(private var responseData: AppointmentResponse?,var type:String) :
    RecyclerView.Adapter<AppointList.AdapterAppointListViewHolder>() {

    lateinit var binding:ItemAppoinmentListBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterAppointListViewHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_appoinment_list,
            parent,false)
        return  AdapterAppointListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAppointListViewHolder, position: Int) {
        binding.date.text=responseData!!.data[position].APPOINTMENT_DATE.replace("-", "\n")
        binding.tvTime.text="Time:"+responseData!!.data[position].APPT_TIME
        binding.tvDrName.text=responseData!!.data[position].DOCTOR
        binding.tvDepartment.text = responseData!!.data[position].DESIGNATION
        if(responseData!!.data[position].TOKEN_NO.isNullOrEmpty()){
            binding.tvStaus.text=responseData!!.data[position].STATUS+"\n--"
        }else{
            binding.tvStaus.text=responseData!!.data[position].STATUS+"\n"+responseData!!.data[position].TOKEN_NO
        }


        if(type=="family"){
            binding.layAppointment.setOnClickListener {
                val intent = Intent(binding.layAppointment.context, BookAppointment_cancel_reschedule::class.java)
                intent.putExtra("type","reschedule")
                intent.putExtra("date",responseData!!.data[position].APPOINTMENT_DATE)
                intent.putExtra("mrno",responseData!!.data[position].MRNO)
                intent.putExtra("token",responseData!!.data[position].APPT_ALLOCATION_ID)
                binding.layAppointment.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return responseData!!.data.size
    }
    class AdapterAppointListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}