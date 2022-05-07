package com.example.kimshealth.model.reportList

data class Data(
    val AUTHORIZATION_DATE: String,
    val BILL_DATE: String,
    val CPR_NO: String,
    val INV_PAT_BILLING_ID: String,
    val INV_PAT_TEST_RESULT_ID_1: String,
    val SAMPLE_COLLECTION_DATE: Any,
    val TESTNAME: String,
    val RESULT_PATH:String
)