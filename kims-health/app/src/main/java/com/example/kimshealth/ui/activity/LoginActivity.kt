package com.example.kimshealth.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ActivityLoginBinding
import com.example.kimshealth.model.ValidateModel
import com.example.kimshealth.model.ValidatePostRequest
import com.example.kimshealth.model.location.LocationModel
import com.example.kimshealth.model.login.LoginRequest
import com.example.kimshealth.model.login.LoginResponse
import com.example.kimshealth.model.sendSms.RequestSendSms
import com.example.kimshealth.model.sendSms.ResponseSendSms
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.Contants.apiCall
import com.example.kimshealth.utils.Contants.showToast
import com.example.kimshealth.utils.PrefManager
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    var selectedCountryCode: String? =null


    var countryList:String=""
    var selectedLocation:String=""
    var serverOTP:String=""
    val arrayList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.layValidateOtp.visibility=View.GONE

        binding.btnSendOtp.setOnClickListener {

            if(!binding.phoneNumberEdt.text.isNullOrEmpty()){
                if(binding.phoneNumberEdt.text.length<7){
                    binding.phoneNumberEdt.error = "Please Enter Valid Mobile Number"
                }
                else{
                    binding.laySendOtp.visibility=View.GONE
                    binding.layValidateOtp.visibility=View.VISIBLE
                    binding.tvNumberConfirm.text = "Code sent to $selectedCountryCode "+binding.phoneNumberEdt.text
                    Contants.isLoader().showProgress(this)
                    //
                    apiSendSMS(""+binding.phoneNumberEdt.text,""+selectedLocation)
                }

            }else{
                binding.phoneNumberEdt.error = "Mobile Number is Required"
            }

        }
        binding.tvClear.setOnClickListener {
            if(binding.pinview.isNotEmpty()){
                binding.pinview.clearValue()
            }
        }
        binding.tvEditNumber.setOnClickListener{
            binding.laySendOtp.visibility=View.VISIBLE
            binding.layValidateOtp.visibility=View.GONE
        }
        binding.btnValidate.setOnClickListener {
            if(!selectedCountryCode.isNullOrEmpty()){
                if(binding.pinview.isNotEmpty()){
                    if(binding.pinview.value==serverOTP){
                        apiUserLogin(selectedCountryCode+binding.phoneNumberEdt.text)
                    }else{
                         showToast(this,"Your Otp is "+serverOTP)
                        //  binding.pinview.value=""
                    }
                }else{
                    showToast(this,"Your Otp is "+serverOTP)
                }
            }else{
                Toast.makeText(this,"Please Select Country Code",Toast.LENGTH_LONG).show()
            }

          //  showToast(this@LoginActivity,selectedCountryCode+binding.phoneNumberEdt.text)

        }
        binding.etCoutryCode.setOnClickListener {
          //  showCountryPop()
        }
        Contants.isLoader().showProgress(this)
        apivalidate()

        binding.etPsCountryCode.setOnSpinnerItemSelectedListener(object :
            OnSpinnerItemSelectedListener<String?> {
            override fun onItemSelected(
                oldIndex: Int,
                oldItem: String?,
                newIndex: Int,
                newItem: String?
            ) {
                Log.e("Value Selected",""+newItem.toString())
                    selectedCountryCode=newItem.toString().split("-")[0]
                    selectedLocation=newItem.toString().split("-")[1]
                    binding.etPsCountryCode.text=selectedCountryCode
            }
        })
    }

    fun apivalidate() {
        var requestDataLogin=ValidatePostRequest("!Lov3MyPiano","kimsMobileApp")
        var validateModel: Call<ValidateModel> =apiCall.getToken(requestDataLogin)
        validateModel.enqueue(object : Callback<ValidateModel?> {
            override fun onResponse(call: Call<ValidateModel?>?, response: Response<ValidateModel?>) {
                if (response.isSuccessful) {
                   Log.e("Login",""+response.body())  // have your all data
                    PrefManager(this@LoginActivity).setBarrerToken(response.body()!!.desc)
                    apiLoadCoutrycode()
                } else Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show() // this will tell you why your api doesnt work most of time
            }

            override fun onFailure(call: Call<ValidateModel?>, t: Throwable) {

                Log.e("Login Response",""+t.toString())
                Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()// ALL NETWORK ERROR HERE
            }
        })

    }

    fun apiLoadCoutrycode(){
        Log.e("LoginData",""+PrefManager(this@LoginActivity).getBarrerToken())
        var locationModel: Call<LocationModel> =apiCall.getCountryList(""+PrefManager(this@LoginActivity).getBarrerToken())
        locationModel.enqueue(object : Callback<LocationModel?> {
            override fun onResponse(call: Call<LocationModel?>?, response: Response<LocationModel?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                  //   arrayList1: ArrayList<String> = ArrayList() // This Use To Maintain Coutry Code List
                    val orderNumbers: List<String> = ArrayList()

                    for (element in response.body()!!.data) {
                        arrayList.add(element.country_code+"-"+element.loc_name)
                    }

                    Log.e("DataTime",""+arrayList)
                    binding.etPsCountryCode.setItems(arrayList.toHashSet().toList())
                } else Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<LocationModel?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
                Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
        })



    }

    fun showCountryPop(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.custom_layout_spinner, null)
        dialogBuilder.setView(dialogView)

        val adp: ArrayAdapter<String>
        adp = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            arrayList
        )
        val alertDialog = dialogBuilder.create()
        val spinner = dialogView.findViewById<View>(R.id.custom_spiner) as Spinner
        spinner.adapter = adp
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long) {
                        selectedCountryCode=parent.getItemAtPosition(position).toString().split("-")[0]
                        selectedLocation=parent.getItemAtPosition(position).toString().split("-")[1]
                        val nametxt = findViewById<EditText>(R.id.et_coutry_code)
                        nametxt.setText(selectedCountryCode)
                            Toast.makeText(
                                parent.context,
                                "Selected: $selectedCountryCode",
                                Toast.LENGTH_LONG
                            ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        alertDialog.show()
    }

    fun apiSendSMS(mobileNumber: String, location: String){

        Log.e("SMS",""+PrefManager(this@LoginActivity).getBarrerToken()+mobileNumber)

        var ResponseSendSms: Call<ResponseSendSms> =apiCall.sendsms(""+PrefManager(this@LoginActivity).getBarrerToken(),
            RequestSendSms(location,mobileNumber)
        )
        ResponseSendSms.enqueue(object : Callback<ResponseSendSms?> {
            override fun onResponse(call: Call<ResponseSendSms?>?, response: Response<ResponseSendSms?>) {
                Log.e("Get OTP",""+response.body())
                if (response.body()!=null) {
                    Contants.isLoader().hideProgress()
                    serverOTP= response.body()!!.otp.toString()
                } else  Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<ResponseSendSms?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response","Invalid Credentials")
                Toast.makeText(this@LoginActivity,"No Data Found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun apiUserLogin(mobileNumber: String){
        Log.e("LoginData",""+PrefManager(this@LoginActivity).getBarrerToken()+mobileNumber)

        var loginResponse: Call<LoginResponse> =apiCall.userLogin(""+PrefManager(this@LoginActivity).getBarrerToken(),
            LoginRequest(mobileNumber)
        )
        loginResponse.enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>?, response: Response<LoginResponse?>) {
                Log.e("Get Location",""+response.body())
                if (response.body()!!.data!=null) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                    PrefManager(this@LoginActivity).setMRNO(""+response.body()!!.data.MRNO)
                    PrefManager(this@LoginActivity).setPhoneNumber(""+response.body()!!.data.MOBILEPHONE)
                    PrefManager(this@LoginActivity).setName(""+response.body()!!.data.FULL_NAME)
                    PrefManager(this@LoginActivity).setImage(""+response.body()!!.data.IMR_URL)
                    PrefManager(this@LoginActivity).setCPR(""+response.body()!!.data.CPR)
                    PrefManager(this@LoginActivity).setGender(""+response.body()!!.data.GENDER)
                    var intent= Intent(this@LoginActivity,DashboardActivity::class.java)
                    startActivity(intent)

                } else  Toast.makeText(this@LoginActivity,"No Registered User Found", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response","Invalid Credentials")
                Toast.makeText(this@LoginActivity,"No Registered User Found", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}