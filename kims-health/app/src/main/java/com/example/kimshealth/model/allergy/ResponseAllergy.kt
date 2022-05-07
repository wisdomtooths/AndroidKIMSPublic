package com.example.kimshealth.model.allergy

data class ResponseAllergy(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)