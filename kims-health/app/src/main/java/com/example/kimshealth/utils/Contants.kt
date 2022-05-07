package com.example.kimshealth.utils

import android.content.Context
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.kimshealth.network.ApiHelper
import com.example.kimshealth.network.ApiInterface


object Contants {
    var PatientName:String="null"
    var PatientGender:String="null"
    var locationSelected="RBH"

    var PatientMrno:String=""
    var PatinetCPR:String=""
    var PatinetDepartmentName:String=""
    var PatinetDepartmentId:String=""
    var PatinetDoctorName:String=""
    var PatinetDoctortId:String=""
    var PatientImage:String="http://87.252.100.230:8087/kims/assets/Male.png"


    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    val baseurl="http://87.252.100.230:8087/kims/"

    val apiCall=ApiHelper.getInstance().create(ApiInterface::class.java)

    fun textalert(context: Context,message: String){
         SweetAlertDialog(context)
            .setTitleText(message)
            .show()
    }

    fun isLoader():CustomProgress{
        var customProgress=CustomProgress.getInstance()
        return customProgress
    }

}
