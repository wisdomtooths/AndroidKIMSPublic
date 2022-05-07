package com.example.kimshealth.ui.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdpaterTimeSlot
import com.example.kimshealth.databinding.ActivityBookAppointmentDrProfileBinding
import com.example.kimshealth.model.appointmentDetails.RequestAppointmentDetails
import com.example.kimshealth.model.appointmentDetails.ResponseAppointmentDetails
import com.example.kimshealth.model.makeappointment.MakeAppoinementResponse
import com.example.kimshealth.model.makeappointment.RequestMakeAppoinment
import com.example.kimshealth.model.rescheduleappointment.RequestReschedule
import com.example.kimshealth.model.rescheduleappointment.ResponseReschedule
import com.example.kimshealth.model.timeslots.Data
import com.example.kimshealth.model.timeslots.RequestTimeSlot
import com.example.kimshealth.model.timeslots.ResponseTimeSlot
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BookAppointmentDrProfile : AppCompatActivity() {
    lateinit var binding:ActivityBookAppointmentDrProfileBinding
    var selectedTime="00"
    var schedule:String=""
    var doctorID:String=""
    var seletedDate=""

    var doctorImage:String=""
    var doctorName1:String=""
    var doctordes:String=""
    var dateString:String=""

    lateinit var responseAppointmentDetails:ResponseAppointmentDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_book_appointment_dr_profile)
        //setContentView(R.layout.activity_book_appointment_dr_profile)

        if(intent.getStringExtra("DEPARTMENT")!=null) {
            doctorImage=intent.getStringExtra("IMAGE").toString()
            doctorName1=intent.getStringExtra("DRNAME").toString()
            doctordes=intent.getStringExtra("DEPARTMENT").toString()

            binding.profileTvDep.text=intent.getStringExtra("DEPARTMENT")
            Picasso.get().
            load(intent.getStringExtra("IMAGE")).placeholder(R.drawable.pic).
            into( binding.drImage)
            binding.profileDrName.text=intent.getStringExtra("DRNAME")
            binding.profileTvDesig.text=intent.getStringExtra("DESIGNATION")
        }
        binding.iconBack.setOnClickListener {
            Log.e("Test","OnBackPress")
            this.finish()
        }

        schedule= intent.getStringExtra("type")!!

//        intent.putExtra("type","reschedule")
//        intent.putExtra("date",responseData!!.data[position].APPOINTMENT_DATE)
//        intent.putExtra("mrno",responseData!!.data[position].MRNO)
//        intent.putExtra("token",responseData!!.data[position].TOKEN_NO)

        if(intent.getStringExtra("type")=="reschedule"){
            binding.btnBookAppointemnt.text="Reschedule"
            Contants.isLoader().showProgress(this)
            Log.e("DATE",""+intent.getStringExtra("date").toString()+"|"+intent.getStringExtra("mrno").toString())
            apiappointDetails(""+intent.getStringExtra("date").toString(),
                ""+intent.getStringExtra("mrno").toString(),
                ""+intent.getStringExtra("token").toString())

        }
        binding.calendarView.setOnClickListener {
            showDate()
        }

        binding.btnBookAppointemnt.setOnClickListener {
            if(selectedTime!=="00"){
                if(intent.getStringExtra("type")=="reschedule"){
                    usermessage("New Reschedule Requested","New Reschedule Requested,\n" +
                            "we will notify you once its confirmed")
                }else{
                    newDialog(this)
//                    usermessage("New Appointment Requested","New Appointment Requested,\n" +
//                            "we will notify you once its confirmed")
                }
            }
            else{
                Toast.makeText(this,"Please select time slot",Toast.LENGTH_LONG).show()
            }
        }
    }


    fun showDate(){
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        var datePickerDialog = DatePickerDialog(this@BookAppointmentDrProfile,
            {
                    datePicker, year, month, day ->
                //2022-04-20
              //  val sdf = SimpleDateFormat("dd/MMMM/yyyy")
                val sdf1 = SimpleDateFormat("yyyy-MMMM-dd")
                dateString = sdf1.format(calendar.time)

                seletedDate=""+year+"-"+(month + 1)+"-"+day

                binding.calendarView.text = seletedDate

                apiDrtimeslot()
            }, year, month, dayOfMonth)
        Contants.isLoader().showProgress(this)
        datePickerDialog.show()
    }

    fun usermessage(title:String, msg:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            if(schedule=="reschedule"){
                Contants.isLoader().showProgress(this)
                apiappointReschdeule()
            }else{
                Contants.isLoader().showProgress(this)
                apiappointMentList()
            }
        }
        builder.show()
    }

    fun apiappointMentList(){
        val displayFormat = SimpleDateFormat("HH:mm")
        val parseFormat = SimpleDateFormat("hh:mm a")
        val datefomated = parseFormat.parse(selectedTime)
    //    println(parseFormat.format(datefomated).toString() + " = " + displayFormat.format(datefomated))

        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
        var requestMakeAppoinment= RequestMakeAppoinment(
            "create",
            ""+displayFormat.format(datefomated).toString(),
            ""+seletedDate,
            ""+Contants.PatinetDoctorName,
             Contants.PatinetDepartmentId.toInt(),
             Contants.PatinetDoctortId.toInt(),
            ""+ Contants.PatinetCPR,
            ""+ Contants.PatientMrno)
        Log.e("MakeAppoitment",""+requestMakeAppoinment.toString())
        var makeAppoinementResponse: Call<MakeAppoinementResponse> = Contants.apiCall.
        makeappointment(""+ PrefManager(applicationContext).getBarrerToken(),
            requestMakeAppoinment
        )

        makeAppoinementResponse.enqueue(object : Callback<MakeAppoinementResponse?> {
            override fun onResponse(call: Call<MakeAppoinementResponse?>?, response: Response<MakeAppoinementResponse?>) {
                if (response.isSuccessful) {
//                    val snackbar = Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "Appointment Created Successfully",
//                        Snackbar.LENGTH_LONG
//                     )
//                    snackbar.setAction("View", View.OnClickListener {
//
//                    })
                    //    snackbar.show()
                    Toast.makeText(applicationContext,""+response.body()!!.desc,Toast.LENGTH_LONG).show()
                    var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                    Log.e("Make Appointment",""+response.body())

                } else {
                    // Tempory Commented
                    Log.e("Make Appointment",""+response)
                    Log.e("Make Appointment",""+response.errorBody()!!.string())

                    Toast.makeText(applicationContext,""+response.body()!!.desc,Toast.LENGTH_LONG).show()

                    var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<MakeAppoinementResponse?>, t: Throwable) {
                Log.e("Make Appointment",""+t.toString())
                Contants.isLoader().hideProgress()
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
                var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
                intent.putExtra("type",schedule)
                startActivity(intent)
                finish()
            }
        })
    }

    fun apiDrtimeslot(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var doctorResponse: Call<ResponseTimeSlot> = Contants.apiCall.
        getdrTimeSlot(""+ PrefManager(this.applicationContext).getBarrerToken(),
            RequestTimeSlot(binding.calendarView.text.toString(),Contants.PatinetDoctortId)
        )

        doctorResponse.enqueue(object : Callback<ResponseTimeSlot?> {
            override fun onResponse(call: Call<ResponseTimeSlot?>?, response: Response<ResponseTimeSlot?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Log.e("Get Location",""+response.body())
                    var responseTimeSlotforMorning:ResponseTimeSlot
                    var responseTimeSlotforEvening:ResponseTimeSlot

                    var listdataforMorning= arrayListOf<Data>()
                    var listdataforEvening= arrayListOf<Data>()
                    for (value in response.body()!!.data){
                        if(value.SESSION_NAME.toString()=="Morning"){
                            listdataforMorning.add(Data(""+value.ALLOCATION_TIME,""+value.SESSION_NAME,value.SLOT_DURATION))
                        }else{
                            listdataforEvening.add(Data(""+value.ALLOCATION_TIME,""+value.SESSION_NAME,value.SLOT_DURATION))
                        }
                    }
                    responseTimeSlotforMorning= ResponseTimeSlot(listdataforMorning.size,listdataforMorning,"success")
                    responseTimeSlotforEvening=ResponseTimeSlot(listdataforEvening.size,listdataforEvening,"success")

                    selectedTime="00"
                    binding.rvTimeslotMorning.removeAllViews()
                    var adaptermorning= AdpaterTimeSlot(responseTimeSlotforMorning,this@BookAppointmentDrProfile)
                    binding.rvTimeslotMorning.adapter=adaptermorning
                    binding.rvTimeslotMorning.layoutManager= LinearLayoutManager(this@BookAppointmentDrProfile, LinearLayoutManager.HORIZONTAL,false)


                    binding.rvTimeslotEvening.removeAllViews()
                    var adapterevening= AdpaterTimeSlot(responseTimeSlotforEvening,this@BookAppointmentDrProfile)
                    binding.rvTimeslotEvening.adapter=adapterevening
                    binding.rvTimeslotEvening.layoutManager= LinearLayoutManager(this@BookAppointmentDrProfile, LinearLayoutManager.HORIZONTAL,false)



                } else Toast.makeText(this@BookAppointmentDrProfile, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
            }
            override fun onFailure(call: Call<ResponseTimeSlot?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Dr Response",""+t.toString())
                Toast.makeText(this@BookAppointmentDrProfile, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun seletedTimeslot(timeslot:String){
        selectedTime=timeslot
    }
    fun getselectedTimeSlot():String{
        return selectedTime
    }

    fun apiappointDetails(appdate:String,mrno:String,token:String){

        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
        var requestMakeAppoinment= RequestAppointmentDetails(
            ""+appdate,
            ""+mrno,
            ""+token)

        Log.e("Request",""+requestMakeAppoinment.toString())
        var makeAppoinementResponse: Call<ResponseAppointmentDetails> = Contants.apiCall.
        getappointmentdetails(""+ PrefManager(applicationContext).getBarrerToken(),
            requestMakeAppoinment
        )

        makeAppoinementResponse.enqueue(object : Callback<ResponseAppointmentDetails?> {
            override fun onResponse(call: Call<ResponseAppointmentDetails?>?, response: Response<ResponseAppointmentDetails?>) {
                Contants.isLoader().hideProgress()
                if (response.isSuccessful) {
                    responseAppointmentDetails=response.body()!!
                    Log.e("Response",""+response.body())
                    binding.profileDrName.text = response.body()!!.data[0].DOCTOR
                    binding.tvTime.text = response.body()!!.data[0].APPT_TIME
                    Contants.PatinetDoctortId=response.body()!!.data[0].DOCTOR_ID.toString()
                } else {
                    Contants.isLoader().hideProgress()
                    Log.e("Response",""+response.errorBody()!!.string())
                }
            }
            override fun onFailure(call: Call<ResponseAppointmentDetails?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Response",""+t.toString())
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }


    fun apiappointReschdeule(){

        val displayFormat = SimpleDateFormat("HH:mm")
        val parseFormat = SimpleDateFormat("hh:mm a")
        val datefomated = parseFormat.parse(selectedTime)

        Log.e("Token",""+ PrefManager(applicationContext).getBarrerToken())
        var requestMakeAppoinment= RequestReschedule(
            "reschedule",
            ""+seletedDate,
            ""+displayFormat.format(datefomated).toString(),
            ""+responseAppointmentDetails.data[0].APPOINTMENT_DATE,
            ""+responseAppointmentDetails.data[0].APPT_TIME,
            ""+responseAppointmentDetails.data[0].CPR,
            responseAppointmentDetails.data[0].DEPT_ID,
            responseAppointmentDetails.data[0].DOCTOR_ID,
            ""+responseAppointmentDetails.data[0].DOCTOR,
            ""+responseAppointmentDetails.data[0].MRNO,
            ""+responseAppointmentDetails.data[0].APPT_ALLOCATION_ID
            )
        Log.e("MakeAppoitment",""+requestMakeAppoinment.toString())
        var makeAppoinementResponse: Call<ResponseReschedule> = Contants.apiCall.
        makereschdeule(""+ PrefManager(applicationContext).getBarrerToken(),
            requestMakeAppoinment
        )

        makeAppoinementResponse.enqueue(object : Callback<ResponseReschedule?> {
            override fun onResponse(call: Call<ResponseReschedule?>?, response: Response<ResponseReschedule?>) {
                if (response.isSuccessful) {
                    Contants.isLoader().hideProgress()
                    Toast.makeText(applicationContext,""+response.body()!!.desc,Toast.LENGTH_LONG).show()
                    Log.e("Response Appointment",""+response.body())
                    var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                    Log.e("Make Appointment",""+response.body())

                } else {
                    Contants.isLoader().hideProgress()
                    // Tempory Commented
                    Log.e("Make Appointment",""+response)
                    Log.e("Make Appointment",""+response.errorBody()!!.string())

                    Toast.makeText(applicationContext,""+response.body()!!.desc,Toast.LENGTH_LONG).show()

                    var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
                    intent.putExtra("type",schedule)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<ResponseReschedule?>, t: Throwable) {
                Log.e("Make Appointment",""+t.toString())
                Contants.isLoader().hideProgress()
                Toast.makeText(applicationContext, "Unable to Load Data", Toast.LENGTH_SHORT)
                    .show()
//                var intent= Intent(this@BookAppointmentDrProfile,DashboardActivity::class.java)
//                intent.putExtra("type",schedule)
//                startActivity(intent)
//                finish()
            }
        })
    }

    fun newDialog(c: Context?) {
        val alertDialog: AlertDialog
        val layoutInflater = LayoutInflater.from(c)
        var  dialogueView = layoutInflater.inflate(R.layout.activity_book_appoinment_check_out, null)
        val alertDialogBuilder = AlertDialog.Builder(c!!,R.style.DialogTheme)
        alertDialogBuilder.setView(dialogueView)
        alertDialog = alertDialogBuilder.create()

        var iconback=dialogueView.findViewById(R.id.icon_back) as ImageView
        iconback.setOnClickListener {
            alertDialog.dismiss()
        }
        var iv_image=dialogueView.findViewById(R.id.iv_image) as ImageView
        Picasso.get().
        load(doctorImage).placeholder(R.drawable.pic).
        into( iv_image)

        var doctorName=dialogueView.findViewById(R.id.dr_name) as TextView
        doctorName.text=Contants.PatinetDoctorName

        var doctor_des=dialogueView.findViewById(R.id.doctor_des) as TextView
        doctor_des.text=doctordes

        var appointmentTime=dialogueView.findViewById(R.id.appointmentTime) as TextView
        appointmentTime.text = ""+selectedTime

        var tv_patientName=dialogueView.findViewById(R.id.tv_patientName) as TextView
        tv_patientName.text = ""+Contants.PatientName

        var flagDate=dialogueView.findViewById(R.id.flag_tv_date) as TextView

        flagDate.text = ""+seletedDate.replace("-", "\n")

        var btn_confirm=dialogueView.findViewById(R.id.btn_confirm) as Button
        btn_confirm.setOnClickListener {
            usermessage("Appointment Created Successfully","Appointment Created Successfully,\n" +
                            "Go to dashboard")
        }

        alertDialog.show()
    }
}