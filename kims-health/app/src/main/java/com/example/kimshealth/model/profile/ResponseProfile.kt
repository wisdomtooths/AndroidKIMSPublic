package com.example.kimshealth.model.profile

data class ResponseProfile(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)