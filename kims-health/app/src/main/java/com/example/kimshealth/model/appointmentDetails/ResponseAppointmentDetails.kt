package com.example.kimshealth.model.appointmentDetails

data class ResponseAppointmentDetails(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)