package com.example.kimshealth.model.doctor

data class DoctorResponse(
    val count: Int,
    val data: List<Data>,
    val message: String
)