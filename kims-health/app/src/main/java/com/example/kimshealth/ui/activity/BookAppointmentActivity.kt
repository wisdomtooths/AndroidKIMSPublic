package com.example.kimshealth.ui.activity

import android.graphics.Movie
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterDrList
import com.example.kimshealth.adapter.AdapterDr_By_Speciality
import com.example.kimshealth.databinding.ActivityBookAppointmentBinding
import com.example.kimshealth.model.deparment.Data
import com.example.kimshealth.model.deparment.DeparmentRequest
import com.example.kimshealth.model.deparment.DepartmentResponse
import com.example.kimshealth.model.doctor.DoctorResponse
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookAppointmentActivity : AppCompatActivity() {
    lateinit var binding:ActivityBookAppointmentBinding
    var currentStatus="departmentList"

    lateinit var adpaterDrList:AdapterDr_By_Speciality

    lateinit var departmentResponse: DepartmentResponse
    lateinit var departmentResponseFilter: DepartmentResponse


    lateinit var filteredList: MutableList<Data>


    lateinit var adapterDrList:AdapterDrList


    lateinit var filteredListDr: MutableList<com.example.kimshealth.model.doctor.Data>
    lateinit var doctorResponsemain:DoctorResponse
    lateinit var doctorResponsemaineFilter: DoctorResponse


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_book_appointment)

        filteredList= ArrayList()
        filteredListDr=ArrayList()

             Picasso.get().
             load(Contants.PatientImage).placeholder(R.drawable.pic).
             into(binding.profileImage)

        binding.tvTypedoctor.setOnClickListener {
            binding.tvSearchData.setText("")
            binding.tvTypedoctor.setBackgroundResource(R.drawable.bg_rounded)
            binding.tvTypespeciality.setBackgroundResource(0)
            currentStatus="drlist"
          // binding.layDrByName.visibility=View.VISIBLE 
         // binding.layDrBySpecility.visibility=View.GONE

            binding.rvDrBySpeciality.removeAllViews()
            Contants.isLoader().showProgress(this)
            apiDrtList()
        }

        binding.tvTypespeciality.setOnClickListener {
            binding.tvSearchData.setText("")
            binding.tvTypespeciality.setBackgroundResource(R.drawable.bg_rounded)
            binding.tvTypedoctor.setBackgroundResource(0)
            currentStatus="departmentList"
            binding.rvDrBySpeciality.removeAllViews()
            Contants.isLoader().showProgress(this)
            apiDeparmenttList()

            //binding.layDrBySpecility.visibility=View.VISIBLE
           // binding.layDrByName.visibility=View.GONE
        }

        binding.tvSearchData.addTextChangedListener(textWatcher)

//        binding.tvSearchData.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                binding.tvSearchData.setText(""+s.toString())
//            }
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//
////                if(currentStatus=="departmentList"){
////                    for (value in departmentResponse.data) {
////                        if(value.MEDICAL_DEPARTMENT.contentEquals(s)){
////                            departmentResponseFilter.data=departmentResponse.data
////                            adpaterDrList.onDataChanged(departmentResponseFilter)
////                        }
////                    }
////
////                }
//            }
//        })
        
//        binding.layDrBySpecility.setOnClickListener {
//            val intent = Intent(this, BookAppointmentBySpeciality::class.java)
//            startActivity(intent)
//        }
        Contants.isLoader().showProgress(this)
        apiDeparmenttList()
    }


    fun apiDrtList(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var doctorResponse: Call<DoctorResponse> = Contants.apiCall.
        getDrList(""+ PrefManager(this.applicationContext).getBarrerToken(),
           DeparmentRequest("RBH",""))


        doctorResponse.enqueue(object : Callback<DoctorResponse?> {
            override fun onResponse(call: Call<DoctorResponse?>?, response: Response<DoctorResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                  //  val adpate= AdapterDr_By_Speciality()
                    doctorResponsemain=response.body()!!
                    adapterDrList=AdapterDrList(doctorResponsemain)
                    binding.rvDrBySpeciality.adapter=adapterDrList
                    binding.rvDrBySpeciality.layoutManager=
                        LinearLayoutManager(this@BookAppointmentActivity, LinearLayoutManager.VERTICAL,false)
                } else Toast.makeText(this@BookAppointmentActivity, "No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<DoctorResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Dr Response",""+t.toString())
                Toast.makeText(this@BookAppointmentActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT)
//                .show()
        //    Log.e("Test1",doctorResponsemain.toString())
            Log.e("VALUE in TEXT",binding.tvSearchData.text.toString())

            if(currentStatus=="departmentList"){
                filteredList.clear()
                    for (value in departmentResponse.data) {
                        Log.e("BookAppointment-Data",value.MEDICAL_DEPARTMENT)
                        if(value.MEDICAL_DEPARTMENT.contains(binding.tvSearchData.text.toString(),ignoreCase = true)){
                            Log.e("BookAppointment","INSIDE DEPARTMENT")
                            filteredList.add(Data(
                                ""+value.DEPARTMENT_ICON,
                                 value.DEPT_ID,
                                ""+value.MEDICAL_DEPARTMENT
                            ))

                        }
                    }
                Log.e("Filter Array",""+filteredList)
                binding.rvDrBySpeciality.removeAllViews()
                departmentResponseFilter=DepartmentResponse(filteredList.size,filteredList,"")
                Log.e("Get Location",""+departmentResponse)
                adpaterDrList= AdapterDr_By_Speciality(departmentResponseFilter)
                binding.rvDrBySpeciality.layoutManager= GridLayoutManager(this@BookAppointmentActivity, 2)
                binding.rvDrBySpeciality.adapter=adpaterDrList

               // adpaterDrList.onDataChanged(departmentResponseFilter)
                }
            else{
                filteredListDr.clear()
                for (value in doctorResponsemain.data) {
                    Log.e("BookAppointment-Data",value.FIRST_NAME)

                    var tempDrName:String=value.FIRST_NAME+" "+value.LAST_NAME

                   // filteredListDr.clear()
                    // if(tempDrName.contains(binding.tvSearchData.text.toString(),ignoreCase = true))
                    if(tempDrName.contains(binding.tvSearchData.text.toString(),ignoreCase = true)){
                        Log.e("BookAppointment","INSIDE Dr Filter")
                        filteredListDr.add(com.example.kimshealth.model.doctor.Data(
                            ""+value.DEPT,
                            ""+value.DESIGNATION,
                            ""+value.FIRST_NAME,
                            ""+value.GENDER,
                            ""+value.IMG_URL,
                            ""+value.LAST_NAME,
                            ""+value.QLFN,
                            ""+value.DOCTOR_ID,
                            ""+value.DEPT_ID
                        ))

                        Log.e("Test1",filteredListDr.toString())
                    }
                }
                Log.e("Filter Array",""+filteredListDr)
                var doctorResponsemaineFilter= DoctorResponse(filteredListDr.size,filteredListDr,"")
                Log.e("Filter Array New",""+doctorResponsemaineFilter)
                adapterDrList=AdapterDrList(doctorResponsemaineFilter)
                binding.rvDrBySpeciality.removeAllViews()
                binding.rvDrBySpeciality.adapter=adapterDrList
                binding.rvDrBySpeciality.layoutManager=
                    LinearLayoutManager(this@BookAppointmentActivity, LinearLayoutManager.VERTICAL,false)
              //  adapterDrList.onDataChanged(doctorResponsemaineFilter)
            }
        }
    }


    fun apiDeparmenttList(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var deparmentRequest: Call<DepartmentResponse> = Contants.apiCall.
        getDeparmentList(""+ PrefManager(this.applicationContext).getBarrerToken(),
            DeparmentRequest("RBH",""))

        deparmentRequest.enqueue(object : Callback<DepartmentResponse?> {
            override fun onResponse(call: Call<DepartmentResponse?>?, response: Response<DepartmentResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    departmentResponse= response.body()!!
                    Log.e("Get Location",""+departmentResponse)
                    adpaterDrList= AdapterDr_By_Speciality(response.body())
                    binding.rvDrBySpeciality.layoutManager= GridLayoutManager(this@BookAppointmentActivity, 2)
                    binding.rvDrBySpeciality.adapter=adpaterDrList

//                    val adpaterAppointmentSchecule=AdpaterAppointmentSchecule(response.body())
//                    binding.rvHomeSchedule.adapter=adpaterAppointmentSchecule

                } else{
                    Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                        .show()

                }
            }
            override fun onFailure(call: Call<DepartmentResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
                Toast.makeText(this@BookAppointmentActivity, t.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}