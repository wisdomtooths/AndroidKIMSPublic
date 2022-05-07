package com.example.kimshealth.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdpaterFamilyList
import com.example.kimshealth.databinding.ItemAppointmentBottomSheetBinding
import com.example.kimshealth.model.family.FamilyMemberResponse
import com.example.kimshealth.model.family.FamilyRequest
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemApointmentBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding:ItemAppointmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.item_appointment_bottom_sheet,container,true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.ivImage.setOnClickListener {
//            val intent = Intent (requireActivity(), BookAppointmentActivity::class.java)
//            requireActivity().startActivity(intent)
//        }

        binding.rvFamilyList.layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        Contants.isLoader().showProgress(activity)
        apiFamilyList()

    }

    fun apiFamilyList(){
        Log.e("Token",""+ PrefManager(requireContext().applicationContext).getBarrerToken())
//        var appointResponse: Call<FamilyMemberResponse> = Contants.apiCall.
//        getFamilyList(""+ PrefManager(requireContext().applicationContext).getBarrerToken(),
//            FamilyRequest(""+PrefManager(requireContext().applicationContext).getPHONENUMBER(),""+PrefManager(requireContext().applicationContext).getMRNO())
//        );
        var appointResponse: Call<FamilyMemberResponse> = Contants.apiCall.
        getFamilyList(""+ PrefManager(requireContext().applicationContext).getBarrerToken(),
            FamilyRequest("+97317115000","002930321")
        )

        appointResponse.enqueue(object : Callback<FamilyMemberResponse?> {
            override fun onResponse(call: Call<FamilyMemberResponse?>?, response: Response<FamilyMemberResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                    var adpaterFamilyList=AdpaterFamilyList(response.body())
                    binding.rvFamilyList.adapter=adpaterFamilyList

                } else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<FamilyMemberResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}