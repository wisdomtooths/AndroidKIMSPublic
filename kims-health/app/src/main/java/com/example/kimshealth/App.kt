package com.example.kimshealth

import android.app.Application
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog




class App: Application() {
    override fun onCreate() {
        super.onCreate()
        inistlized()
    }

    private fun inistlized() {
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
    }
}