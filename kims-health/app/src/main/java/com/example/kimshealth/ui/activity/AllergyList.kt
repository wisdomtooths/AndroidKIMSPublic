package com.example.kimshealth.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterAllergy
import com.example.kimshealth.databinding.ActivityAllergyListBinding
import com.example.kimshealth.model.allergy.RequestAllergy
import com.example.kimshealth.model.allergy.ResponseAllergy
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllergyList : AppCompatActivity() {
    lateinit var binding:ActivityAllergyListBinding
    lateinit var mrno:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_allergy_list)
        mrno= intent.getStringExtra("mrno")!!
        Contants.isLoader().showProgress(this)
        apiallergyList()

    }
    fun apiallergyList(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var allergyResponse: Call<ResponseAllergy> = Contants.apiCall.
        getAllergyList(""+ PrefManager(this.applicationContext).getBarrerToken(),
           RequestAllergy("allergy",mrno)
        )
        allergyResponse.enqueue(object : Callback<ResponseAllergy?> {
            override fun onResponse(call: Call<ResponseAllergy?>?, response: Response<ResponseAllergy?>) {
                if (response.isSuccessful) {
                    Log.e("Get Location",""+response.body())
                    Contants.isLoader().hideProgress()
                    if(response.body()!!.data.isEmpty()){
                        Toast.makeText(this@AllergyList, "No Allergy Information Data found", Toast.LENGTH_SHORT)
                    .show()
                    }else{
                        val adapterAllergy= AdapterAllergy(response.body())
                        binding.rvAllergy.adapter=adapterAllergy
                        binding.rvAllergy.layoutManager=
                            LinearLayoutManager(this@AllergyList)

                    }

                }
                //
                //                else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
//                    .show()
            }
            override fun onFailure(call: Call<ResponseAllergy?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Response",""+t.toString())
//                Toast.makeText(context, "Unknown Error: No Appointment Found", Toast.LENGTH_SHORT)
//                    .show()
            }
        })
    }
}