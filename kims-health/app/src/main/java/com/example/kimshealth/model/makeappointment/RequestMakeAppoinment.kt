package com.example.kimshealth.model.makeappointment

data class RequestMakeAppoinment(
    val action: String,
    val appt_time: String,
    val appt_date: String,
    val doctor_name: String,
    val department_id: Int,
    val doctor_id: Int,
    val cpr: String,
    val mrno: String,
)

//"mrno": "000985117",
//"cpr": "901326240",
//"doctor_id" : 880,
//"department_id" : 13,
//"doctor_name" : "Sunil",
//"cancel_date" : "2022-04-15",
//"cancel_time" : "10:15",
//"appt_date" : "2022-04-20",
//"appt_time" : "11:30",
//"action" : "reschedule"


// Request Cancel Appointment
//data class RequestMakeAppointment(
//    val action: String,
//    val appt_date: String,
//    val appt_time: String,
//    val dept_name: String,
//    val doctor_name: String,
//    val loc_name: String,
//    val mrno: String,
//    val patient_gender: String,
//    val patient_name: String
//)
//Request Reschedule