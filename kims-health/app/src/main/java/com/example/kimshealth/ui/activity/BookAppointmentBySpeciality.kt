package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterDrList
import com.example.kimshealth.databinding.ActivityBookAppointmentBySpecialityBinding
import com.example.kimshealth.model.deparment.DeparmentRequest
import com.example.kimshealth.model.doctor.DoctorResponse
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookAppointmentBySpeciality : AppCompatActivity() {
   lateinit var binding: ActivityBookAppointmentBySpecialityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_book_appointment_by_speciality)

//        binding.laySelectedDr.setOnClickListener {
//            val intent = Intent(this, BookAppointmentDrProfile::class.java)
//            startActivity(intent)
//        }
        if(intent.getStringExtra("DEPT")!=null){
            Contants.isLoader().showProgress(this)
            apiDrtList(intent.getStringExtra("DEPT")!!)
        }else{
            Contants.isLoader().showProgress(this)
            apiDrtList("")
        }

    }
    fun apiDrtList(dept:String){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var doctorResponse: Call<DoctorResponse> = Contants.apiCall.
        getDrList(""+ PrefManager(this.applicationContext).getBarrerToken(),
            DeparmentRequest("RBH",dept)
        )


        doctorResponse.enqueue(object : Callback<DoctorResponse?> {
            override fun onResponse(call: Call<DoctorResponse?>?, response: Response<DoctorResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                    //  val adpate= AdapterDr_By_Speciality()

                    var adapterDrList= AdapterDrList(response.body())
                    binding.rvDrList.adapter=adapterDrList
                    binding.rvDrList.layoutManager=
                        LinearLayoutManager(this@BookAppointmentBySpeciality, LinearLayoutManager.VERTICAL,false)
                } else Toast.makeText(this@BookAppointmentBySpeciality, "No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<DoctorResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
                Toast.makeText(this@BookAppointmentBySpeciality, "No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}