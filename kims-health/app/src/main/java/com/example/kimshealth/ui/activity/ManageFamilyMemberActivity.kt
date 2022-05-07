package com.example.kimshealth.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterManageFamily
import com.example.kimshealth.databinding.ActivityManageFamilyMemberBinding
import com.example.kimshealth.model.family.FamilyMemberResponse
import com.example.kimshealth.model.family.FamilyRequest
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageFamilyMemberActivity : AppCompatActivity() {
    lateinit var binding:ActivityManageFamilyMemberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_manage_family_member)
        binding.rvManagefamilyMember.layoutManager=
            GridLayoutManager(this, 2)
        Contants.isLoader().showProgress(this)
        apiFamilyList()
    }
    fun apiFamilyList(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var appointResponse: Call<FamilyMemberResponse> = Contants.apiCall.
        getFamilyList(""+ PrefManager(this.applicationContext).getBarrerToken(),
            FamilyRequest("+97317115000","002930321")
        )
        appointResponse.enqueue(object : Callback<FamilyMemberResponse?> {
            override fun onResponse(call: Call<FamilyMemberResponse?>?, response: Response<FamilyMemberResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("MANAGE FAMILY",""+response.body())
                    var adpaterFamilyList= AdapterManageFamily(response.body())
                    binding.rvManagefamilyMember.adapter=adpaterFamilyList

                } else Toast.makeText(this@ManageFamilyMemberActivity, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<FamilyMemberResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Response",""+t.toString())
                Toast.makeText(this@ManageFamilyMemberActivity, t.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}