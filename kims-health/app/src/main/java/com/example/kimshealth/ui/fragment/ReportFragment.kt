package com.example.kimshealth.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.Constants
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterReports
import com.example.kimshealth.databinding.FragmentReportBinding
import com.example.kimshealth.model.reportList.ReportRequest
import com.example.kimshealth.model.reportList.ReportResponse
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class ReportFragment:  Fragment() {

    lateinit var reportBinding: FragmentReportBinding

    var fromDate:String=""
    var toDate:String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportBinding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_report,container,false)
        return reportBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reportBinding.rvReportList.layoutManager=
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)


        reportBinding.tvToDate.setOnClickListener {
            showtoDate()
        }
        reportBinding.tvFromDate.setOnClickListener {
            showfromDate()
        }

        reportBinding.tvMail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, "kimsinfoLoc2@KIMS.com")
            startActivity(Intent.createChooser(emailIntent, "Send someone an email..."))
        }


        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val formattedDate = df.format(c)

//
        val months: Long = 3
        val seconds = months * 31556952L / 12
        val milliseconds = seconds * 1000

        val sdf = SimpleDateFormat("dd-MMM-yyyy")
        val datefrom = sdf.format(System.currentTimeMillis()-milliseconds)


        reportBinding.tvFromDate.text=datefrom
        reportBinding.tvToDate.text=formattedDate

        Contants.isLoader().showProgress(requireActivity())
        apiReportList()
    }

    fun apiReportList(){
        Log.e("Token",""+ PrefManager(requireContext().applicationContext).getBarrerToken())
        var reportResponse: Call<ReportResponse> = Contants.apiCall.
        getReportList(""+ PrefManager(requireContext().applicationContext).getBarrerToken(),
           ReportRequest("reports",Contants.PatientMrno,reportBinding.tvFromDate.text.toString(),reportBinding.tvToDate.text.toString()
        ))

        Log.e("RequestReport",""+reportResponse.toString())

        reportResponse.enqueue(object : Callback<ReportResponse?> {
            override fun onResponse(call: Call<ReportResponse?>?, response: Response<ReportResponse?>) {
                Contants.isLoader().hideProgress()
                if (response.isSuccessful) {
                    Log.e("Report",""+response.body())
                    Contants.isLoader().hideProgress()
                    val reportAdapter= AdapterReports(response.body())
                    reportBinding.rvReportList.adapter=reportAdapter

                } else {
                    Contants.isLoader().hideProgress()
                    Log.e("Report",""+response.errorBody())
                    Toast.makeText(context, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Reports",""+toString())
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    fun showfromDate() {
        val months: Long = 3
        val seconds = months * 31556952L / 12
        val milliseconds = seconds * 1000


        val calendar = Calendar.getInstance()
        val upTo = calendar.timeInMillis
        calendar.set(2018, 12, 31)
        val startFrom = calendar.timeInMillis



        val constraints = CalendarConstraints.Builder().
             setOpenAt(upTo-milliseconds)
            .setStart(startFrom)
            .setEnd(upTo)
            .build()

        val datePicker: MaterialDatePicker<Long> = MaterialDatePicker
            .Builder
            .datePicker().setCalendarConstraints(constraints)
            .build()
        datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {

            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val date = sdf.format(it)
            reportBinding.tvFromDate.text = date
            Contants.isLoader().showProgress(requireActivity())
            apiReportList()
        }
    }
    fun showtoDate() {
        val calendar = Calendar.getInstance()
        val upTo = calendar.timeInMillis
        calendar.set(2019, 1, 1)

        val startFrom = calendar.timeInMillis
        val constraints = CalendarConstraints.Builder()
            .setStart(startFrom)
            .setEnd(upTo)
            .setValidator(
             DateValidatorPointBackward.now())
            .build()

        val datePicker: MaterialDatePicker<Long> = MaterialDatePicker
            .Builder
            .datePicker().setCalendarConstraints(constraints)
            .build()

        datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
            val date = sdf.format(it)
            reportBinding.tvToDate.text = date
            Contants.isLoader().showProgress(requireActivity())
            apiReportList()
        }
    }
}



