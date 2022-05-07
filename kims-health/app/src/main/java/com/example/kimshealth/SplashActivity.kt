package com.example.kimshealth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.databinding.ActivitySplashBinding
import com.example.kimshealth.model.ValidateModel
import com.example.kimshealth.model.ValidatePostRequest
import com.example.kimshealth.ui.activity.IntroActivity
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    var binding: ActivitySplashBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_splash)

        apivalidate()
    }
    fun apivalidate() {
        var requestDataLogin= ValidatePostRequest("!Lov3MyPiano","kimsMobileApp")
        var validateModel: Call<ValidateModel> = Contants.apiCall.getToken(requestDataLogin)
        validateModel.enqueue(object : Callback<ValidateModel?> {
            override fun onResponse(call: Call<ValidateModel?>?, response: Response<ValidateModel?>) {
                if (response.isSuccessful) {
                    Log.e("Login",""+response.body())  // have your all data
                    PrefManager(this@SplashActivity).setBarrerToken(response.body()!!.desc)
                    val intent = Intent(this@SplashActivity, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                } else Toast.makeText(this@SplashActivity, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show() // this will tell you why your api doesnt work most of time
            }

            override fun onFailure(call: Call<ValidateModel?>, t: Throwable) {

                Log.e("Login Response",""+t.toString())
                Toast.makeText(this@SplashActivity, t.toString(), Toast.LENGTH_SHORT)
                    .show() // ALL NETWORK ERROR HERE
            }
        })

    }
}