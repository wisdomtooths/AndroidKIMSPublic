package com.example.kimshealth.model.sendSms

data class RequestSendSms(
    val loc_code: String,
    val mobile_number: String
)