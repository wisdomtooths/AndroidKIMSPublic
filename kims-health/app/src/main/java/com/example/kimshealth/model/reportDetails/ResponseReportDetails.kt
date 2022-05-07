package com.example.kimshealth.model.reportDetails

data class ResponseReportDetails(
    val count: Int,
    val `data`: List<Data>,
    val message: String
)