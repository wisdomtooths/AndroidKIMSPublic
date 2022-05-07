package com.example.kimshealth.model.appointment

data class AppointmentRequest(
    val dateFlag: String,
    val mrno: String,
    val page:Int,
    val limit:Int
)