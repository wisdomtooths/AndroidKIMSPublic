package com.example.kimshealth.model.appointment

data class AppointmentResponse(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)