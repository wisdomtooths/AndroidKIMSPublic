package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ActivityBookingAppointmentConfimBinding

class BookingAppointmentConfim : AppCompatActivity() {
    lateinit var binding:ActivityBookingAppointmentConfimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_booking_appointment_confim)
        binding.btnDashborad.setOnClickListener {
            var intent= Intent(this,DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}