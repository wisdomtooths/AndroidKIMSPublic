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
import com.example.kimshealth.adapter.AdpaterAppointmentSchecule
import com.example.kimshealth.databinding.FragmentHomeBinding
import com.example.kimshealth.model.appointment.AppointmentRequest
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.ui.activity.BookAppointmentActivity
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileName.text = PrefManager(requireContext()).getName()

        binding.cardBookAppointment.setOnClickListener {
            val myView: View = requireActivity().findViewById(R.id.bottom_nav_appointment)
            myView.performClick()
        }
        binding.cardReports.setOnClickListener {
            val myView: View = requireActivity().findViewById(R.id.botton_nav_report)
            myView.performClick()
        }

        binding.findadoctor.setOnClickListener {
            val intent = Intent(requireContext(), BookAppointmentActivity::class.java)
            startActivity(intent)
        }
        Picasso.get().
        load(PrefManager(requireActivity()).getImage()).placeholder(R.drawable.pic).
        into(binding.profileImage)

        binding.rvHomeSchedule.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        Contants.isLoader().showProgress(activity)
        apiappointMentList()
    }
    fun apiappointMentList(){
        Log.e("Token",""+PrefManager(requireContext().applicationContext).getBarrerToken())
        var appointResponse: Call<AppointmentResponse> = Contants.apiCall.
                        getAppointmentList(""+PrefManager(requireContext().applicationContext).getBarrerToken(),
                        AppointmentRequest("future",""+PrefManager(requireContext().applicationContext).getMRNO(),0,20))

        Log.e("Request",""+appointResponse)
        appointResponse.enqueue(object : Callback<AppointmentResponse?> {
            override fun onResponse(call: Call<AppointmentResponse?>?, response: Response<AppointmentResponse?>) {
                if (response.isSuccessful) {
                    Log.e("Get Location",""+response.body())
                     Contants.isLoader().hideProgress()
                    val adpaterAppointmentSchecule=AdpaterAppointmentSchecule(response.body())
                    binding.rvHomeSchedule.adapter=adpaterAppointmentSchecule

                }
            }
            override fun onFailure(call: Call<AppointmentResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Login Response",""+t.toString())
//                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
//                    .show()
            }
        })
    }

}