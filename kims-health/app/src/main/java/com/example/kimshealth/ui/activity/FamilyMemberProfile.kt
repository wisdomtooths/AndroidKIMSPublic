package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ActivityBookAppoinmentCheckOutBinding
import com.example.kimshealth.databinding.ActivityFamilyMemberProfileBinding
import com.example.kimshealth.utils.Contants
import com.squareup.picasso.Picasso

class FamilyMemberProfile : AppCompatActivity() {
    lateinit var binding: ActivityFamilyMemberProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_family_member_profile)

        binding.profileMemberName.text=intent.getStringExtra("name")
        binding.memberMrno.text="MRNO: "+intent.getStringExtra("mrno")
        binding.profileMemberContact.text=""+intent.getStringExtra("mobile")
        binding.titleMemberName.text=intent.getStringExtra("name")

        Contants.PatinetCPR=intent.getStringExtra("CPR").toString()
        Contants.PatientMrno=intent.getStringExtra("mrno").toString()
        Contants.PatientGender=intent.getStringExtra("gender").toString()
        Contants.PatientName=intent.getStringExtra("name").toString()

        Picasso.get().
        load(intent.getStringExtra("image")).placeholder(R.drawable.pic).
        into( binding.memberImage)

        binding.tvMemberAppoinment.setOnClickListener {
            var intent= Intent(this@FamilyMemberProfile,FamilyMemberAppointment::class.java)
            intent.putExtra("mrno",Contants.PatientMrno)
            startActivity(intent)
            finish()
        }
        binding.btnMemberProfileAllergy.setOnClickListener {
            var intent= Intent(this@FamilyMemberProfile,AllergyList::class.java)
            intent.putExtra("mrno","001080113")
            startActivity(intent)
            finish()
        }
        binding.btnMemberProfileReports.setOnClickListener {
            var intent= Intent(this@FamilyMemberProfile,DashboardActivity::class.java)
            intent.putExtra("direct","reports")
            intent.putExtra("mrno",Contants.PatientMrno)
            startActivity(intent)
            finish()
        }

//        intent.putExtra("name",responseData!!.data[position].FULL_NAME)
//        intent.putExtra("image",responseData!!.data[position].IMR_URL)
//        intent.putExtra("mobile",responseData!!.data[position].MOBILEPHONE)
//        intent.putExtra("mrno",responseData!!.data[position].MRNO)
    }
}