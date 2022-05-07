package com.example.kimshealth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemAllergyBinding
import com.example.kimshealth.databinding.ItemSearchDrBySpecialityBinding
import com.example.kimshealth.model.allergy.ResponseAllergy

class AdapterAllergy(var responseAllergy: ResponseAllergy?) :RecyclerView.Adapter<AdapterAllergy.AdapterAllergyViewHolder>() {
    lateinit var binding: ItemAllergyBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAllergyViewHolder {
        binding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_allergy,parent,false)
        return AdapterAllergyViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAllergyViewHolder, position: Int) {
        binding.allergyName.text = ""+responseAllergy!!.data[position].ALLERGY_NAME
    }

    override fun getItemCount(): Int {
       return responseAllergy!!.data.size
    }
    class AdapterAllergyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}