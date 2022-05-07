package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemFamilyBinding
import com.example.kimshealth.model.family.FamilyMemberResponse
import com.example.kimshealth.ui.activity.BookAppointmentActivity
import com.example.kimshealth.utils.Contants
import com.squareup.picasso.Picasso

class AdpaterFamilyList(private var responseData: FamilyMemberResponse?) :

    RecyclerView.Adapter<AdpaterFamilyList.AdpaterFamilyListHolder>() {
    lateinit var binding:ItemFamilyBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdpaterFamilyListHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_family,
            parent,false)
        return  AdpaterFamilyListHolder(binding.root)
    }

    override fun getItemCount(): Int {
       return responseData!!.data.size
    }

    override fun onBindViewHolder(holder: AdpaterFamilyListHolder, position: Int) {
        //binding.ivImage
        Picasso.get().
            load(responseData!!.data[position].IMR_URL).placeholder(R.drawable.pic).
        into(binding.ivImage)

        binding.tvFamilyName.text=responseData!!.data[position].FULL_NAME

                binding.ivImage.setOnClickListener {
                    Contants.PatientName=responseData!!.data[position].FULL_NAME
                    Contants.PatientGender=responseData!!.data[position].GENDER
                    Contants.PatientMrno=responseData!!.data[position].MRNO
                    Contants.PatinetCPR=""+responseData!!.data[position].CPR
                    Contants.PatientImage=""+responseData!!.data[position].IMR_URL

                    val intent = Intent (binding.ivImage.context, BookAppointmentActivity::class.java)
                    binding.ivImage.context.startActivity(intent)
          }


    }
    class AdpaterFamilyListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}