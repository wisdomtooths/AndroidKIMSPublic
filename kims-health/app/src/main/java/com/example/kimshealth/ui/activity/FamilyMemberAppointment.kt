package com.example.kimshealth.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AppointList
import com.example.kimshealth.databinding.FragmentAppoinmentBinding
import com.example.kimshealth.model.appointment.AppointmentRequest
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.ui.fragment.ItemApointmentBottomSheet
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FamilyMemberAppointment : AppCompatActivity() {

    lateinit var appoinmentBinding: FragmentAppoinmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appoinmentBinding= DataBindingUtil.setContentView(this,R.layout.fragment_appoinment)
        appoinmentBinding.tvUpcomingSchedule.setOnClickListener {
            appoinmentBinding.tvUpcomingSchedule.setBackgroundResource(R.drawable.bg_rounded)
            appoinmentBinding.tvViewhistory.setBackgroundResource(0)
            Contants.isLoader().showProgress(this)
            apiappointMentList(intent.getStringExtra("mrno").toString(),"future")
        }
        appoinmentBinding.tvViewhistory.setOnClickListener {
            appoinmentBinding.tvUpcomingSchedule.setBackgroundResource(0)
            appoinmentBinding.tvViewhistory.setBackgroundResource(R.drawable.bg_rounded)
            Contants.isLoader().showProgress(this)
            apiappointMentList(intent.getStringExtra("mrno").toString(),"old")
        }
        appoinmentBinding.layCreateAppointment.setOnClickListener {
            val intent = Intent (this, BookAppointmentActivity::class.java)
            startActivity(intent)
        }
        appoinmentBinding.rvAppoinetmentList.layoutManager=
            LinearLayoutManager(this)
        Contants.isLoader().showProgress(this)
        apiappointMentList(intent.getStringExtra("mrno").toString(),"future")
        appoinmentBinding.toolbar.visibility=View.VISIBLE
    }
    fun apiappointMentList(mrno:String,type:String){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        Log.e("MRNO",""+mrno)
        var appointResponse: Call<AppointmentResponse> = Contants.apiCall.
        getAppointmentList(""+ PrefManager(this.applicationContext).getBarrerToken(),
            AppointmentRequest(type,""+mrno,0,10)
        )
        appointResponse.enqueue(object : Callback<AppointmentResponse?> {
            override fun onResponse(call: Call<AppointmentResponse?>?, response: Response<AppointmentResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    if(!response.body()!!.data.isNullOrEmpty()){
                        Log.e("Get Location",""+response.body())

                        val adpaterAppointmentSchecule= AppointList(response.body(),"family")
                        appoinmentBinding.rvAppoinetmentList.adapter=adpaterAppointmentSchecule
                    }else{
                        Toast.makeText(this@FamilyMemberAppointment, "No Appointment Found", Toast.LENGTH_SHORT)
//                        .show()
                    }


                }else{
//                    Toast.makeText(this@FamilyMemberAppointment, "Unknown Error: No Data Found", Toast.LENGTH_SHORT)
//                        .show()
                }
            }
            override fun onFailure(call: Call<AppointmentResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
//                Toast.makeText(this@FamilyMemberAppointment, "Unknown Error: No Data Found", Toast.LENGTH_SHORT)
//                    .show()
            }
        })
    }
}