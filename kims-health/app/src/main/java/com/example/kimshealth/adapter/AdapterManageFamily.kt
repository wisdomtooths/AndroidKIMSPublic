package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemFamilyBinding
import com.example.kimshealth.databinding.ItemManageFamilyMemberBinding
import com.example.kimshealth.model.family.FamilyMemberResponse
import com.example.kimshealth.ui.activity.BookAppointmentActivity
import com.example.kimshealth.ui.activity.FamilyMemberProfile
import com.example.kimshealth.utils.Contants
import com.squareup.picasso.Picasso

class AdapterManageFamily(private var responseData: FamilyMemberResponse?) :RecyclerView.Adapter<AdapterManageFamily.AdapterManageFamilyViewHolder>() {

    lateinit var binding: ItemManageFamilyMemberBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterManageFamilyViewHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_manage_family_member,
            parent,false)
        return AdapterManageFamily.AdapterManageFamilyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterManageFamilyViewHolder, position: Int) {
        Picasso.get().
        load(responseData!!.data[position].IMR_URL).placeholder(R.drawable.pic).
        into(binding.ivImage)

        binding.tvMfamilyName.text=responseData!!.data[position].FULL_NAME
        binding.tvMfamilymrno.text=responseData!!.data[position].MRNO


        binding.ivImage.setOnClickListener {
            Contants.PatientName=responseData!!.data[position].FULL_NAME
            Contants.PatientGender=responseData!!.data[position].GENDER
            val intent = Intent (binding.ivImage.context, FamilyMemberProfile::class.java)
            intent.putExtra("name",responseData!!.data[position].FULL_NAME)
            intent.putExtra("image",responseData!!.data[position].IMR_URL)
            intent.putExtra("mobile",responseData!!.data[position].MOBILEPHONE)
            intent.putExtra("CPR",responseData!!.data[position].CPR)
            intent.putExtra("mrno",responseData!!.data[position].MRNO)
            intent.putExtra("gender",responseData!!.data[position].GENDER)
            binding.ivImage.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return responseData!!.data.size
    }
    class AdapterManageFamilyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}