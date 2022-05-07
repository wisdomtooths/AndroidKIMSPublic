package com.example.kimshealth.model.reportList

data class ReportRequest(
    val module: String,
    val mrno: String,
    val from_date:String,
    val to_date:String
)