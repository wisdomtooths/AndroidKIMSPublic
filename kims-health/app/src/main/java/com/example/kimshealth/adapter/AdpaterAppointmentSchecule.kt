package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemAppointmentScheduleBinding
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.ui.activity.BookAppoinmentCheckOut
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import com.example.kimshealth.ui.activity.BookAppointment_cancel_reschedule
import com.squareup.picasso.Picasso

class AdpaterAppointmentSchecule(private var responseData: AppointmentResponse?) :
    RecyclerView.Adapter<AdpaterAppointmentSchecule.AdpaterAppointmentScheculeHolder>() {
    lateinit var binding:ItemAppointmentScheduleBinding
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AdpaterAppointmentScheculeHolder {

                binding=DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_appointment_schedule,
                    parent,false)

           return AdpaterAppointmentScheculeHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdpaterAppointmentScheculeHolder, position: Int) {

        binding.doctorName.text=responseData!!.data[position].DOCTOR
        binding.tvDate.text=responseData!!.data[position].APPOINTMENT_DATE+","+responseData!!.data[position].APPT_TIME
        binding.scheduleTvStaus.text=responseData!!.data[position].STATUS+"\n"+responseData!!.data[position].TOKEN_NO

        if(!responseData!!.data[position].DOCTOR_IMG_URL.isNullOrEmpty()){
            Picasso.get().
            load(responseData!!.data[position].DOCTOR_IMG_URL).placeholder(R.drawable.pic).
            into(binding.profileImage)
        }


        binding.btnReschedule.setOnClickListener {
//            val intent = Intent(binding.btnReschedule.context, BookAppointmentDrProfile::class.java)
//            intent.putExtra("type","reschedule")
//            intent.putExtra("date",responseData!!.data[position].APPOINTMENT_DATE)
//            intent.putExtra("mrno",responseData!!.data[position].MRNO)
//            intent.putExtra("token",responseData!!.data[position].TOKEN_NO)
//            binding.btnReschedule.context.startActivity(intent)

            val intent = Intent(binding.btnReschedule.context, BookAppointment_cancel_reschedule::class.java)
            intent.putExtra("type","reschedule")
            intent.putExtra("date",responseData!!.data[position].APPOINTMENT_DATE)
            intent.putExtra("image",responseData!!.data[position].DOCTOR_IMG_URL)
            intent.putExtra("mrno",responseData!!.data[position].MRNO)
            intent.putExtra("token",responseData!!.data[position].APPT_ALLOCATION_ID)
            binding.btnReschedule.context.startActivity(intent)
        }
//        binding.layScheduleCard.setOnClickListener {
//            val intent = Intent(binding.btnReschedule.context, BookAppoinmentCheckOut::class.java)
//            binding.btnReschedule.context.startActivity(intent)
//        }
        binding.btnCancel.setOnClickListener {
            val intent = Intent(binding.btnReschedule.context, BookAppointment_cancel_reschedule::class.java)
            intent.putExtra("type","reschedule")
            intent.putExtra("date",responseData!!.data[position].APPOINTMENT_DATE)
            intent.putExtra("image",responseData!!.data[position].DOCTOR_IMG_URL)

            intent.putExtra("mrno",responseData!!.data[position].MRNO)
            intent.putExtra("token",responseData!!.data[position].APPT_ALLOCATION_ID)
            binding.btnReschedule.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return responseData!!.data.size
    }

    class AdpaterAppointmentScheculeHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}