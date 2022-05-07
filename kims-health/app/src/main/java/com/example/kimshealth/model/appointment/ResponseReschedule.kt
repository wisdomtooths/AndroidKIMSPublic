package com.example.kimshealth.model.appointment

data class ResponseReschedule(
    val action: String,
    val appt_date: String,
    val appt_time: String,
    val cancel_date: String,
    val cancel_time: String,
    val cpr: String,
    val department_id: Int,
    val doctor_id: Int,
    val doctor_name: String,
    val mrno: String
)