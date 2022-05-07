package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ActivityBookAppoinmentCheckOutBinding
import com.example.kimshealth.model.makeappointment.MakeAppoinementResponse
import com.example.kimshealth.model.makeappointment.RequestMakeAppoinment
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAppoinmentCheckOut : AppCompatActivity() {
    lateinit var binding:ActivityBookAppoinmentCheckOutBinding
    var schedule:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_book_appoinment_check_out)

        schedule= intent.getStringExtra("type")!!
        if(schedule=="make"){
            binding.btnConfirm.text="Request Confirmation"
        }
        else if(schedule=="reschedule"){
            binding.btnConfirm.text="Request Reschedule"
        }
        else if(schedule=="cancel"){
            binding.btnConfirm.text="Request Cancel"
        }

        binding.btnConfirm.setOnClickListener {
            if(schedule=="make"){
                usermessage("New Appointment Requested","New Appointment Requested,\n" +
                        "we will notify you once its confirmed")
            }
            else if(schedule=="reschedule"){
                usermessage("New Reschedule Requested","New Reschedule Requested,\n" +
                        "we will notify you once its confirmed")
            }
            else if(schedule=="cancel"){
                usermessage("Cancel Requested","Cancel Requested,\n" +
                        "we will notify you once its confirmed")
            }
        }
    }
    fun usermessage(title:String, msg:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Contants.isLoader().showProgress(this)
            apiappointMentList()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }

    fun apiappointMentList(){
        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
        var requestMakeAppoinment= RequestMakeAppoinment(
            "create",
            "08:30",
            "2022-04-20",
            ""+Contants.PatinetDoctorName,
            Contants.PatinetDepartmentId.toInt(),
            Contants.PatinetDoctortId.toInt(),
            ""+ Contants.PatinetCPR,
            ""+ Contants.PatientMrno)
        Log.e("MakeAppoitment",""+requestMakeAppoinment.toString())
        var makeAppoinementResponse: Call<MakeAppoinementResponse> = Contants.apiCall.
        makeappointment(""+ PrefManager(applicationContext).getBarrerToken(),
            requestMakeAppoinment
        )

        makeAppoinementResponse.enqueue(object : Callback<MakeAppoinementResponse?> {
            override fun onResponse(call: Call<MakeAppoinementResponse?>?, response: Response<MakeAppoinementResponse?>) {
                if (response.isSuccessful)
                {
//                    val snackbar = Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "Appointment Created Successfully",
//                        Snackbar.LENGTH_LONG
//                     )
//                    snackbar.setAction("View", View.OnClickListener {
//
//                    })
                    //    snackbar.show()
                    //  Toast.makeText(applicationContext,"Appointment $schedule Successfully",Toast.LENGTH_LONG).show()
                    var intent= Intent(this@BookAppoinmentCheckOut,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                    Log.e("Make Appointment",""+response.body())

                } else {
                   // Tempory Commented
                    Log.e("Make Appointment",""+response)
                    Log.e("Make Appointment",""+response.errorBody()!!.string())

                   Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()

                    var intent= Intent(this@BookAppoinmentCheckOut,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<MakeAppoinementResponse?>, t: Throwable) {
                Log.e("Make Appointment",""+t.toString())
                Contants.isLoader().hideProgress()
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
                var intent= Intent(this@BookAppoinmentCheckOut,DashboardActivity::class.java)
                intent.putExtra("type",schedule)
                startActivity(intent)
                finish()
            }
        })
    }
}