package com.example.kimshealth.model.family

data class FamilyMemberResponse(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)