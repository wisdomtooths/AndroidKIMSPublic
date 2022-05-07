package com.example.kimshealth.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemDrFragmentBinding
import com.example.kimshealth.model.deparment.DepartmentResponse
import com.example.kimshealth.model.doctor.DoctorResponse
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import com.example.kimshealth.utils.Contants
import com.squareup.picasso.Picasso

class AdapterDrList(private var responseData: DoctorResponse?) :RecyclerView.Adapter<AdapterDrList.AdapterDrList>(){

    lateinit var binding:ItemDrFragmentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDrList {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_dr_fragment,
            parent,false)

        return AdapterDrList(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDrList, position: Int) {
        Picasso.get().
        load(responseData!!.data[position].IMG_URL).placeholder(R.drawable.pic).
        into( binding.drImage)

        binding.tvDesn.text=responseData!!.data[position].DESIGNATION
        binding.tvName.text=responseData!!.data[position].FIRST_NAME+" "+responseData!!.data[position].LAST_NAME
        binding.tvConsultant.text=responseData!!.data[position].DEPT


        binding.layDrList.setOnClickListener {

            Contants.PatinetDoctorName=responseData!!.data[position].FIRST_NAME
            Contants.PatinetDoctortId=responseData!!.data[position].DOCTOR_ID

            val intent = Intent(binding.layDrList.context, BookAppointmentDrProfile::class.java)
            intent.putExtra("IMAGE",responseData!!.data[position].IMG_URL)
            intent.putExtra("DRNAME",responseData!!.data[position].FIRST_NAME+" "+responseData!!.data[position].LAST_NAME)
            intent.putExtra("DESIGNATION",responseData!!.data[position].DESIGNATION)
            intent.putExtra("DEPARTMENT",responseData!!.data[position].DEPT)
            intent.putExtra("DOCTOR_ID",responseData!!.data[position].DOCTOR_ID)
            intent.putExtra("DEPT_ID",responseData!!.data[position].DEPT_ID)

            Contants.PatinetDoctorName=responseData!!.data[position].FIRST_NAME+" "+responseData!!.data[position].LAST_NAME
            Contants.PatinetDoctortId=responseData!!.data[position].DOCTOR_ID
            Contants.PatinetDepartmentId=responseData!!.data[position].DEPT_ID
            Contants.PatinetDepartmentName=responseData!!.data[position].DEPT


            intent.putExtra("type","make")
            binding.layDrList.context.startActivity(intent)
        }
    }
    fun onDataChanged(responseDatafilter: DoctorResponse){
        this.responseData=responseDatafilter
        Log.e("Data",""+responseDatafilter)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
      return responseData!!.data.size
    }
    class AdapterDrList(itemView: View) :RecyclerView.ViewHolder(itemView)
}