package com.example.kimshealth.model.timeslots

data class ResponseTimeSlot(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)