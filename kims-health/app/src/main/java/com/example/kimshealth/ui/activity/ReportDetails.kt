package com.example.kimshealth.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.kimshealth.R
import com.example.kimshealth.adapter.AdapterReports
import com.example.kimshealth.databinding.ActivityReportDetailsBinding
import com.example.kimshealth.model.reportDetails.RequestReportDetails
import com.example.kimshealth.model.reportDetails.ResponseReportDetails
import com.example.kimshealth.model.reportList.ReportRequest
import com.example.kimshealth.model.reportList.ReportResponse
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportDetails : AppCompatActivity() {
    var path:String=""
    var billingid:String=""
    var resultID:String=""
    lateinit var binding:ActivityReportDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_report_details)
        Log.e("path",intent.getStringExtra("path").toString())
//        if(!intent.getStringExtra("path").toString().isNullOrEmpty()){
//            path=intent.getStringExtra("path").toString()
//        }

        billingid=intent.getStringExtra("BILLING_ID").toString()
        resultID=intent.getStringExtra("INV_PAT_TEST_RESULT").toString()


        binding.pdfLink.setOnClickListener {
            var intent=Intent(Intent.ACTION_VIEW, Uri.parse(path))
            startActivity(intent)
        }
        Contants.isLoader().showProgress(this)
        apiReportDetails()
    }
    fun apiReportDetails(){
        Log.e("Token",""+ PrefManager(this.applicationContext).getBarrerToken())
        var reportResponse: Call<ResponseReportDetails> = Contants.apiCall.
        getReportDetails(""+ PrefManager(this.applicationContext).getBarrerToken(),
            RequestReportDetails("2235488", Contants.PatientMrno,"996991"))


        reportResponse.enqueue(object : Callback<ResponseReportDetails?> {
            override fun onResponse(call: Call<ResponseReportDetails?>?, response: Response<ResponseReportDetails?>) {
                Contants.isLoader().hideProgress()
                if (response.isSuccessful) {
                    Log.e("Report",""+response.body())
                    Contants.isLoader().hideProgress()
                    path=""+response.body()!!.data[0].RESULT_PATH
                    Log.e("path",path)
                    if(path==null){
                        binding.pdfLink.visibility=View.GONE
                    }
                } else {
                    Contants.isLoader().hideProgress()
                    Log.e("Report",""+response.errorBody())
                    Toast.makeText(this@ReportDetails, response.errorBody()!!.string(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            override fun onFailure(call: Call<ResponseReportDetails?>, t: Throwable) {
                Contants.isLoader().hideProgress()
                Log.e("Reports",""+toString())
                Toast.makeText(this@ReportDetails, t.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}

