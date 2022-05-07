package com.example.kimshealth.model.deparment

data class DepartmentResponse(
    val count: Int,
    var data: List<Data>,
    val message: String
)