package com.example.kimshealth.network

import com.example.kimshealth.utils.Contants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    fun getInstance():Retrofit{
        return  Retrofit.Builder()
             .baseUrl(Contants.baseurl)
             .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
             .addConverterFactory(GsonConverterFactory.create())
             .build()
    }
}