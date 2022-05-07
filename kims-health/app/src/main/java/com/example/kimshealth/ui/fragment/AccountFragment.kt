package com.example.kimshealth.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.kimshealth.R
import com.example.kimshealth.databinding.FragmentAccountBinding
import com.example.kimshealth.databinding.FragmentAppoinmentBinding
import com.example.kimshealth.model.makeappointment.MakeAppoinementResponse
import com.example.kimshealth.model.makeappointment.RequestMakeAppoinment
import com.example.kimshealth.model.profile.RequestProfile
import com.example.kimshealth.model.profile.ResponseProfile
import com.example.kimshealth.ui.activity.DashboardActivity
import com.example.kimshealth.ui.activity.ManageFamilyMemberActivity
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment:  Fragment() {

    lateinit var binding: FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnManagedFamilyMember.setOnClickListener {
            var intent= Intent(requireContext(), ManageFamilyMemberActivity::class.java)
            startActivity(intent)
        }
        Contants.isLoader().showProgress(requireContext())
        apiProfile()
    }
    fun apiProfile(){
        Log.e("Token",""+ PrefManager(requireContext()).getBarrerToken())
        var responseProfile: Call<ResponseProfile> = Contants.apiCall.
        getProfile(""+ PrefManager(requireContext()).getBarrerToken(),
            RequestProfile(""+PrefManager(requireContext()).getPHONENUMBER(),""+ PrefManager(requireContext()).getMRNO())
        )
        responseProfile.enqueue(object : Callback<ResponseProfile?> {
            override fun onResponse(call: Call<ResponseProfile?>?, response: Response<ResponseProfile?>) {
                if (response.isSuccessful)
                {
                    Contants.isLoader().hideProgress()
                    Log.e("Profile Appointment",""+response.body())
                    Picasso.get().
                    load(response.body()!!.data[0].IMR_URL).placeholder(R.drawable.pic).
                    into(binding.profileImage)

                    binding.tvProfileAddress.text = response.body()!!.data[0].ADDRESS
                    binding.tvMrno.text = "MRNO: "+response.body()!!.data[0].MRNO
                  //  binding.tvProfileEmail.setText(response.body()!!.data[0].)
                    binding.tvProfileName.text = response.body()!!.data[0].FULL_NAME
                    if(response.body()!!.data[0].BLOOD_GROUP.isNullOrEmpty()){
                        binding.tvBloodGrp.text="NA"
                    }else{
                        binding.tvBloodGrp.text=response.body()!!.data[0].BLOOD_GROUP
                    }

                    binding.tvProfileNumber.text=response.body()!!.data[0].MOBILEPHONE


                } else {
                    Contants.isLoader().hideProgress()
                    Toast.makeText(requireContext(), "Unable to Load Data", Toast.LENGTH_SHORT)
                        .show()

                }
            }
            override fun onFailure(call: Call<ResponseProfile?>, t: Throwable) {
                Log.e("Make Appointment",""+t.toString())
                Contants.isLoader().hideProgress()

                Toast.makeText(requireContext(), "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}



