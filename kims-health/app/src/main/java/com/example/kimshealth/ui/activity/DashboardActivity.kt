package com.example.kimshealth.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kimshealth.R
import com.example.kimshealth.utils.Contants
import com.example.kimshealth.utils.PrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        val navController: NavController =
            Navigation.findNavController(this, R.id.activity_dashboard_nav_host_fragment)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        var profile_image:ImageView= findViewById<ImageView>(R.id.nav_profile_image)

        Picasso.get().
        load(PrefManager(this).getImage()).placeholder(R.drawable.pic).
        into(profile_image)

        Contants.PatientMrno=PrefManager(this).getMRNO().toString()
        Log.e("report mrno",Contants.PatientMrno)

        if(intent.getStringExtra("direct").toString()=="reports"){
            Log.e("report mrno",Contants.PatientMrno)
            Contants.PatientMrno=intent.getStringExtra("mrno").toString()
            val myView: View = this.findViewById(R.id.botton_nav_report)
            myView.performClick()
        }

        var nav_drawer:DrawerLayout=findViewById(R.id.drawerlyout)
        var hamberger:ImageView=findViewById(R.id.hamberger_icon)
        hamberger.setOnClickListener {
            if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
                nav_drawer.closeDrawer(GravityCompat.START)
            } else {
                nav_drawer.openDrawer(GravityCompat.START)
            }
        }


        var nav_logout:TextView=findViewById(R.id.nav_logout)
        nav_logout.setOnClickListener {
            val builder = AlertDialog.Builder(this@DashboardActivity)
            builder.setMessage("Are you sure you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    PrefManager(this).clearData()
                    var intent= Intent(this@DashboardActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
        var nav_allergy:TextView=findViewById(R.id.nab_allergy)
        nav_allergy.setOnClickListener {
            var intent= Intent(this@DashboardActivity,AllergyList::class.java)
        //    intent.putExtra("mrno","001080113")
            intent.putExtra("mrno","00000")
            startActivity(intent)
        }
        var nav_name:TextView=findViewById(R.id.nav_name)
        nav_name.text = PrefManager(this).getName()

        var nav_editProfile:TextView=findViewById(R.id.profile_edit)
        nav_editProfile.setOnClickListener {
            val myView: View = this.findViewById(R.id.bottom_nav_account)
            myView.performClick()
            if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
                nav_drawer.closeDrawer(GravityCompat.START)
            } else {
                nav_drawer.openDrawer(GravityCompat.START)
            }
        }



        var nav_report:TextView=findViewById(R.id.nav_report)
        nav_report.setOnClickListener {
            val myView: View = this.findViewById(R.id.botton_nav_report)
            myView.performClick()
            if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
                nav_drawer.closeDrawer(GravityCompat.START)
            } else {
                nav_drawer.openDrawer(GravityCompat.START)
            }
        }
        var nav_home:TextView=findViewById(R.id.nav_home)
        nav_home.setOnClickListener {
            val myView: View = this.findViewById(R.id.botton_nav_home)
            myView.performClick()
            if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
                nav_drawer.closeDrawer(GravityCompat.START)
            } else {
                nav_drawer.openDrawer(GravityCompat.START)
            }

        }
    }
}