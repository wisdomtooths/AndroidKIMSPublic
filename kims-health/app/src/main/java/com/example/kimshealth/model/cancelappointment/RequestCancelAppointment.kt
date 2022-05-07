package com.example.kimshealth.model.cancelappointment

data class RequestCancelAppointment(
    val action: String,
    val cancel_date: String,
    val cancel_time: String,
    val cpr: String,
    val department_id: Int,
    val doctor_id: Int,
    val doctor_name: String,
    val mrno: String,
    val appt_allocation_id:String
)