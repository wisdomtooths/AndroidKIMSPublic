package com.example.kimshealth.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var IS_LAUNCHED = "IsLaunched"
    private var BARRIER_TOKEN = "TOKEN"
    private var MRNO="MRNO"
    private var MOBILEPHONE="MOBILEPHONE"
    private var FULL_NAME="FULL_NAME"
    private var IMAGE_URL="IMAGE_URL"
    private var GENDER="GENDER"
    private var CPR ="CPR"

    init {
        if(pref == null) {
            pref = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            editor = pref!!.edit()
        }
    }

    fun setLaunched(isFirstTime: Boolean) {
        editor!!.putBoolean(IS_LAUNCHED, isFirstTime)
        editor!!.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref!!.getBoolean(IS_LAUNCHED, true)
    }

    fun setBarrerToken(token: String){
        editor!!.putString(BARRIER_TOKEN, token)
        editor!!.commit()
    }
    fun getBarrerToken(): String? {
        return pref!!.getString(BARRIER_TOKEN, "")
    }

    fun setMRNO(mrno:String){
        editor!!.putString(MRNO, mrno)
        editor!!.commit()
    }

    fun getMRNO(): String? {
        return pref!!.getString(MRNO, "")
    }

    fun setPhoneNumber(mobile: String){
        editor!!.putString(MOBILEPHONE, mobile)
        editor!!.commit()
    }
    fun getPHONENUMBER(): String? {
        return pref!!.getString(MOBILEPHONE, "")
    }

    fun setName(name: String){
        editor!!.putString(FULL_NAME, name)
        editor!!.commit()
    }
    fun getName(): String? {
        return pref!!.getString(FULL_NAME, "")
    }

    fun clearData(){
        val editor = pref!!.edit()
        editor.clear()
        editor.apply()
    }

    fun setImage(path:String){
        editor!!.putString(IMAGE_URL, path)
        editor!!.commit()
    }
    fun getImage():String?{
        return pref!!.getString(IMAGE_URL, "")
    }

    fun setCPR(cpr:String){
        editor!!.putString(CPR, cpr)
        editor!!.commit()
    }
    fun getCPR():String?{
        return pref!!.getString(CPR, "")
    }

    fun setGender(gender:String){
        editor!!.putString(GENDER, gender)
        editor!!.commit()
    }
    fun getGender():String?{
        return pref!!.getString(GENDER, "")
    }
}