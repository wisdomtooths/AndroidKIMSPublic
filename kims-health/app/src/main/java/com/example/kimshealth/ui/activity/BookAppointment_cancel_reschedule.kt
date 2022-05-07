package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ActivityBookAppointmentCancelRescheduleBinding
import com.example.kimshealth.model.appointmentDetails.RequestAppointmentDetails
import com.example.kimshealth.model.appointmentDetails.ResponseAppointmentDetails
import com.example.kimshealth.model.cancelappointment.RequestCancelAppointment
import com.example.kimshealth.model.cancelappointment.ResponseCancelAppointment
import com.example.kimshealth.model.makeappointment.MakeAppoinementResponse
import com.example.kimshealth.model.makeappointment.RequestMakeAppoinment
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAppointment_cancel_reschedule : AppCompatActivity() {
    lateinit var binding:ActivityBookAppointmentCancelRescheduleBinding
    lateinit var responseAppointmentDetails:ResponseAppointmentDetails

    lateinit var status:String
    lateinit var tempdate:String
    lateinit var tempmrno:String
    lateinit var temptoken:String
    lateinit var tempdrImage:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_book_appointment_cancel_reschedule)


        status=intent.getStringExtra("type").toString()

        Contants.isLoader().showProgress(this)
        apiappointDetails(
            ""+intent.getStringExtra("date").toString(),
            ""+intent.getStringExtra("mrno").toString(),
            ""+intent.getStringExtra("token").toString()
        )
        tempdate=intent.getStringExtra("date").toString()
        tempmrno=intent.getStringExtra("mrno").toString()
        temptoken=intent.getStringExtra("token").toString()
        tempdrImage=intent.getStringExtra("image").toString()
        Picasso.get().
        load(tempdrImage).placeholder(R.drawable.pic).
        into(binding.ivImage)

        binding.btnAppoinmentReschedule.setOnClickListener {

            //Need to pass
            val intent = Intent(this, BookAppointmentDrProfile::class.java)
            intent.putExtra("type","reschedule")
            intent.putExtra("date",tempdate)
            intent.putExtra("mrno",tempmrno)
            intent.putExtra("token",temptoken)
            startActivity(intent)

//            val intent = Intent(this, BookAppointmentDrProfile::class.java)
//            intent.putExtra("type","reschedule")
//            startActivity(intent)
        }
        binding.btnAppoinmentCancel.setOnClickListener {
//            val intent = Intent(this, BookAppointmentDrProfile::class.java)
//            intent.putExtra("type","cancel")
//            startActivity(intent)

            cancelAppoitnment()
        }
    }
    fun cancelAppoitnment(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Appointment Cancellation")
        builder.setMessage("Do you want to cancel the Appointment")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Contants.isLoader().showProgress(this)
            apiappointCancel()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }
    fun apiappointDetails(appdate:String,mrno:String,token:String){

        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
        var requestMakeAppoinment= RequestAppointmentDetails(
            ""+appdate,
            ""+mrno,
            ""+token)
        Log.e("Request",""+requestMakeAppoinment.toString())
        var makeAppoinementResponse: Call<ResponseAppointmentDetails> = Contants.apiCall.
        getappointmentdetails(""+ PrefManager(applicationContext).getBarrerToken(),
            requestMakeAppoinment
        )

        makeAppoinementResponse.enqueue(object : Callback<ResponseAppointmentDetails?> {
            override fun onResponse(call: Call<ResponseAppointmentDetails?>?, response: Response<ResponseAppointmentDetails?>) {
                Contants.isLoader().hideProgress()
                if (response.isSuccessful) {
                    responseAppointmentDetails=response.body()!!
                    Log.e("Response",""+response.body())
                    binding.appointmentDate.text = response.body()!!.data[0].APPT_TIME
                    binding.drDes.text = "Token: "+response.body()!!.data[0].TOKEN_NO
                    binding.bookBy.text = response.body()!!.data[0].PATIENT_NAME
                    binding.dateFlag.text =
                        response.body()!!.data[0].APPOINTMENT_DATE.replace("-", "\n")
                    binding.drName.text = response.body()!!.data[0].DOCTOR

                      //
                     //
                    //  binding.drDes.setText(response.body()!!.data[0].)
                   //  binding.profileDrName.setText(response.body()!!.data[0].DOCTOR)
                  //  binding.tvTime.setText(response.body()!!.data[0].APPT_TIME)
                 //  Contants.PatinetDoctortId=response.body()!!.data[0].DOCTOR_ID.toString()
                } else {
                    Contants.isLoader().hideProgress()
                    Log.e("Response",""+response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<ResponseAppointmentDetails?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Response",""+t.toString())
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }

    fun apiappointCancel(){

        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
      var requestCancelAppointment=RequestCancelAppointment(
          "cancel",
          ""+responseAppointmentDetails.data[0].APPOINTMENT_DATE,
          ""+responseAppointmentDetails.data[0].APPT_TIME,
          ""+responseAppointmentDetails.data[0].CPR,
          responseAppointmentDetails.data[0].DEPT_ID,
          responseAppointmentDetails.data[0].DOCTOR_ID,
          ""+responseAppointmentDetails.data[0].DOCTOR,
          ""+responseAppointmentDetails.data[0].MRNO,
          ""+responseAppointmentDetails.data[0].APPT_ALLOCATION_ID
      )
        Log.e("Request",""+requestCancelAppointment.toString())
        var makeAppoinementResponse: Call<ResponseCancelAppointment> = Contants.apiCall.
        makecancel(""+ PrefManager(applicationContext).getBarrerToken(),
            requestCancelAppointment
        )

        makeAppoinementResponse.enqueue(object : Callback<ResponseCancelAppointment?> {
            override fun onResponse(call: Call<ResponseCancelAppointment?>?, response: Response<ResponseCancelAppointment?>) {
                Contants.isLoader().hideProgress()
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Toast.makeText(this@BookAppointment_cancel_reschedule,response.body()!!.desc,Toast.LENGTH_LONG).show()
                    var intent= Intent(this@BookAppointment_cancel_reschedule,DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseCancelAppointment?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Response",""+t.toString())
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }
}