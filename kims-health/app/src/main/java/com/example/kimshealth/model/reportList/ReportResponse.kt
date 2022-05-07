package com.example.kimshealth.model.reportList

data class ReportResponse(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)