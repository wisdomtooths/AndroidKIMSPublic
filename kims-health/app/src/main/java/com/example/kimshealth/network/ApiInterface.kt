package com.example.kimshealth.network

import com.example.kimshealth.model.ValidateModel
import com.example.kimshealth.model.ValidatePostRequest
import com.example.kimshealth.model.allergy.RequestAllergy
import com.example.kimshealth.model.allergy.ResponseAllergy
import com.example.kimshealth.model.appointment.AppointmentRequest
import com.example.kimshealth.model.appointment.AppointmentResponse
import com.example.kimshealth.model.appointmentDetails.RequestAppointmentDetails
import com.example.kimshealth.model.appointmentDetails.ResponseAppointmentDetails
import com.example.kimshealth.model.cancelappointment.RequestCancelAppointment
import com.example.kimshealth.model.cancelappointment.ResponseCancelAppointment
import com.example.kimshealth.model.deparment.DeparmentRequest
import com.example.kimshealth.model.deparment.DepartmentResponse
import com.example.kimshealth.model.doctor.DoctorRequest
import com.example.kimshealth.model.doctor.DoctorResponse
import com.example.kimshealth.model.family.FamilyMemberResponse
import com.example.kimshealth.model.family.FamilyRequest
import com.example.kimshealth.model.location.LocationModel
import com.example.kimshealth.model.login.LoginRequest
import com.example.kimshealth.model.login.LoginResponse
import com.example.kimshealth.model.makeappointment.MakeAppoinementResponse
import com.example.kimshealth.model.makeappointment.RequestMakeAppoinment
import com.example.kimshealth.model.profile.RequestProfile
import com.example.kimshealth.model.profile.ResponseProfile
import com.example.kimshealth.model.reportDetails.RequestReportDetails
import com.example.kimshealth.model.reportDetails.ResponseReportDetails

import com.example.kimshealth.model.reportList.ReportRequest
import com.example.kimshealth.model.reportList.ReportResponse
import com.example.kimshealth.model.rescheduleappointment.RequestReschedule
import com.example.kimshealth.model.rescheduleappointment.ResponseReschedule
import com.example.kimshealth.model.sendSms.RequestSendSms
import com.example.kimshealth.model.sendSms.ResponseSendSms
import com.example.kimshealth.model.timeslots.RequestTimeSlot
import com.example.kimshealth.model.timeslots.ResponseTimeSlot
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

//    @FormUrlEncoded
//    @POST("validate")
//     fun getToken(
//        @Field("username") username:String,
//        @Field("password") password:String
//    ): Call<ValidateModel>

    @POST("mobile/validate")
    fun getToken(
        @Body validatePostRequest: ValidatePostRequest
    ): Call<ValidateModel>

     @GET("mobile/locations")
     fun getCountryList(
         @Header("Authorization") authHeader:String
     ):Call<LocationModel>


    @POST("mobile/sendsms")
    fun sendsms(
        @Header("Authorization") authHeader:String,
        @Body requestSendSms: RequestSendSms
    ): Call<ResponseSendSms>

    @POST("mobile/login")
    fun userLogin(
        @Header("Authorization") authHeader:String,
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>



    @POST("mobile/appointments")
    fun getAppointmentList(
        @Header("Authorization") authHeader:String,
        @Body appointmentRequest: AppointmentRequest
    ): Call<AppointmentResponse>


    @POST("mobile/family")
    fun getFamilyList(
        @Header("Authorization") authHeader:String,
        @Body familyRequest: FamilyRequest
    ): Call<FamilyMemberResponse>


    @POST("mobile/departments")
    fun getDeparmentList(
        @Header("Authorization") authHeader:String,
        @Body deparmentRequest: DeparmentRequest
    ): Call<DepartmentResponse>


    @POST("mobile/doctors")
    fun getDrList(
        @Header("Authorization") authHeader:String,
        @Body doctorRequest: DeparmentRequest
    ): Call<DoctorResponse>


    @POST("mobile/makeappointment")
    fun makeappointment(
        @Header("Authorization") authHeader:String,
        @Body makeAppoinment: RequestMakeAppoinment
    ): Call<MakeAppoinementResponse>

    @POST("mobile/makeappointment")
    fun makereschdeule(
        @Header("Authorization") authHeader:String,
        @Body makeAppoinment: RequestReschedule
    ): Call<ResponseReschedule>

    @POST("mobile/makeappointment")
    fun makecancel(
        @Header("Authorization") authHeader:String,
        @Body requestCancelAppointment: RequestCancelAppointment
    ): Call<ResponseCancelAppointment>



    @POST("mobile/doctorslots")
    fun getdrTimeSlot(
        @Header("Authorization") authHeader:String,
        @Body requestTimeSlot: RequestTimeSlot
    ): Call<ResponseTimeSlot>


    @POST("mobile/appointments")
    fun getappointmentdetails(
        @Header("Authorization") authHeader:String,
        @Body requestAppointmentDetails: RequestAppointmentDetails
    ): Call<ResponseAppointmentDetails>



    @POST("mobile/profile")
    fun getProfile(
        @Header("Authorization") authHeader:String,
        @Body requestProfile: RequestProfile
    ): Call<ResponseProfile>


    @POST("mobile/healthdata")
    fun getReportList(
        @Header("Authorization") authHeader:String,
        @Body reportRequest: ReportRequest
    ): Call<ReportResponse>

    @POST("mobile/reportdetail")
    fun getReportDetails(
        @Header("Authorization") authHeader:String,
        @Body requestReportDetails: RequestReportDetails
    ): Call<ResponseReportDetails>




    @POST("mobile/healthdata")
    fun getAllergyList(
        @Header("Authorization") authHeader:String,
        @Body requestAllergy: RequestAllergy
    ): Call<ResponseAllergy>


}