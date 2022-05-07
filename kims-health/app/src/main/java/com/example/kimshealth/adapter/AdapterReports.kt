package com.example.kimshealth.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kimshealth.R
import com.example.kimshealth.databinding.ItemReportListBinding
import com.example.kimshealth.model.reportList.ReportResponse
import com.example.kimshealth.ui.activity.DashboardActivity
import com.example.kimshealth.ui.activity.ReportDetails

class AdapterReports(var response: ReportResponse?) :RecyclerView.Adapter<AdapterReports.AdapterReportsViewHolder>(){

    lateinit var binding:ItemReportListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterReportsViewHolder {
        binding= DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_report_list,
            parent,false)
        return AdapterReportsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterReportsViewHolder, position: Int) {
        binding.reportName.text = response!!.data[position].TESTNAME
        binding.reportUploaded.text = "Uploaded On: "+response!!.data[position].AUTHORIZATION_DATE
        binding.layReportTab.setOnClickListener {
            var intent= Intent(binding.layReportTab.context, ReportDetails::class.java)

            intent.putExtra("BILLING_ID",response!!.data[0].INV_PAT_BILLING_ID)
            intent.putExtra("INV_PAT_TEST_RESULT",response!!.data[0].INV_PAT_TEST_RESULT_ID_1)
            binding.layReportTab.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return response!!.count
    }

    class AdapterReportsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}