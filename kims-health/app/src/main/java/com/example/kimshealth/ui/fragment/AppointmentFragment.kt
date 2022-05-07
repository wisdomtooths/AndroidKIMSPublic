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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AppointList
import com.example.kimshealth.databinding.FragmentAppoinmentBinding
import com.example.kimshealth.model.appointment.AppointmentRequest
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.ui.activity.BookAppointmentActivity
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentFragment : Fragment() {

    lateinit var appoinmentBinding:FragmentAppoinmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appoinmentBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_appoinment,container,false)
        return appoinmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            appoinmentBinding.tvUpcomingSchedule.setOnClickListener {
                appoinmentBinding.tvUpcomingSchedule.setBackgroundResource(R.drawable.bg_rounded)
                appoinmentBinding.tvViewhistory.setBackgroundResource(0)
                Contants.isLoader().showProgress(requireActivity())
                apiappointMentList("future")
            }
        appoinmentBinding.tvViewhistory.setOnClickListener {
            appoinmentBinding.tvUpcomingSchedule.setBackgroundResource(0)
            appoinmentBinding.tvViewhistory.setBackgroundResource(R.drawable.bg_rounded)
            Contants.isLoader().showProgress(activity)
            appoinmentBinding.rvAppoinetmentList.removeAllViews()
            apiappointMentList("old")
        }
        appoinmentBinding.layCreateAppointment.setOnClickListener {
            Contants.PatientName=PrefManager(requireActivity()).getName().toString()
            Contants.PatientGender=PrefManager(requireActivity()).getGender().toString()
            Contants.PatientMrno=PrefManager(requireActivity()).getMRNO().toString()
            Contants.PatinetCPR=""+PrefManager(requireActivity()).getCPR().toString()
            val intent = Intent (requireActivity(), BookAppointmentActivity::class.java)
            requireActivity().startActivity(intent)

//            val modalBottomSheet = ItemApointmentBottomSheet()
//            modalBottomSheet.show(requireActivity().supportFragmentManager," ModalBottomSheet.TAG")
        }
        appoinmentBinding.rvAppoinetmentList.layoutManager=
            LinearLayoutManager(context)
        Contants.isLoader().showProgress(activity)
        apiappointMentList("future")
    }
    fun apiappointMentList(type:String){
        Log.e("Token",""+ PrefManager(requireContext().applicationContext).getBarrerToken())
        var appointResponse: Call<AppointmentResponse> = Contants.apiCall.
        getAppointmentList(""+ PrefManager(requireContext().applicationContext).getBarrerToken(),
            AppointmentRequest(type,""+ PrefManager(requireContext().applicationContext).getMRNO(),0,10)
        )
        appointResponse.enqueue(object : Callback<AppointmentResponse?> {
            override fun onResponse(call: Call<AppointmentResponse?>?, response: Response<AppointmentResponse?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    if(response.body()!!.data.isEmpty()){
                        Toast.makeText(requireActivity(), "No Information found", Toast.LENGTH_SHORT)
                    }else{
                        Log.e("Get Location",""+response.body())
                        val adpaterAppointmentSchecule= AppointList(response.body(),"")
                        appoinmentBinding.rvAppoinetmentList.adapter=adpaterAppointmentSchecule
                    }
               }
                //
                //                else Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
//                    .show()
            }
            override fun onFailure(call: Call<AppointmentResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
//                Toast.makeText(context, "Unknown Error: No Appointment Found", Toast.LENGTH_SHORT)
//                    .show()
            }
        })
    }
}