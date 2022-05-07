package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemSearchDrBySpecialityBinding
import com.example.kimshealth.model.deparment.DepartmentResponse
import com.example.kimshealth.ui.activity.BookAppointmentBySpeciality
import com.example.kimshealth.ui.activity.BookAppointmentDrProfile
import com.example.kimshealth.ui.activity.DashboardActivity
import com.example.kimshealth.utils.Contants
import com.squareup.picasso.Picasso

class AdapterDr_By_Speciality(var responseData: DepartmentResponse?) :
    RecyclerView.Adapter<AdapterDr_By_Speciality.AdapterDr_By_SpecialityHolder>() {
    lateinit var binding:ItemSearchDrBySpecialityBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDr_By_SpecialityHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_search_dr_by_speciality,
            parent,false)
        return AdapterDr_By_SpecialityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterDr_By_SpecialityHolder, position: Int) {
        Picasso.get().
        load(responseData!!.data[position].DEPARTMENT_ICON).placeholder(R.drawable.pic).
        into(binding.ivImageIcon)
        binding.tvType.text=responseData!!.data[position].MEDICAL_DEPARTMENT

        binding.layCard.setOnClickListener {

            Contants.PatinetDepartmentId=""+responseData!!.data[position].DEPT_ID
            Contants.PatinetDepartmentName=""+responseData!!.data[position].MEDICAL_DEPARTMENT

              var intent= Intent(binding.layCard.context, BookAppointmentBySpeciality::class.java)
                  intent.putExtra("DEPT",responseData!!.data[position].MEDICAL_DEPARTMENT)
                 binding.layCard.context.startActivity(intent)
        }
       // BookAppointmentBySpeciality

    }

    override fun getItemCount(): Int {
      return responseData!!.data.size
        //return 5
    }
    fun onDataChanged(responseDatafilter: DepartmentResponse){
        this.responseData=responseDatafilter
        notifyDataSetChanged()
    }

    class AdapterDr_By_SpecialityHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    )
}