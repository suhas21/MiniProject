package com.example.suhas.mini1

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.SourceandDestination.SourceDestinationPOJO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
   private var mAuth: FirebaseAuth? = null
    private var year:Int=0
    private var month:Int=0
    private var date:Int=0
    private lateinit var calendar:Calendar
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        calendar= Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)
        date=calendar.get(Calendar.DATE)
        showdate(year,month+1,date)
        val cmm=ConnectivityHelper()
        if (cmm.isConnectingToInternet(this@MainActivity)) {
        } else {
            var sbar= Snackbar.make(mainlayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        mAuth = FirebaseAuth.getInstance()
    }
    private fun showdate(year:Int,month:Int,date:Int)
    {
        val s:StringBuilder=StringBuilder().append(date).append("-").append(month).append("-").append(year)
        choosendate.text=s.toString()
    }
    fun selectdate(v: View)
    {
        createdDialog(999)!!.show()
    }
    fun loadResultDate(v:View)
    {
        val svalue=sourcestation.text.toString()
        val destvalue=deststaion.text.toString()
        val datevalue=choosendate.text.toString()
        when {
            svalue.isEmpty() -> sourcestation.error="This Field Should Not be Empty"
            destvalue.isEmpty() -> deststaion.error="This Field Should Not be Empty"
            else -> {
                val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.railwayapi.com/").build()
                val a5 = r.create(SrcDestInff::class.java)
                val call5 = a5.getDetails(svalue, destvalue, datevalue)
                call5.enqueue(object : retrofit2.Callback<SourceDestinationPOJO> {
                    override fun onFailure(call: Call<SourceDestinationPOJO>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<SourceDestinationPOJO>, response: Response<SourceDestinationPOJO>) {
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                        val list = mutableListOf<String>()
                        val tvalues = response.body()
                        list.add("Total " + tvalues!!.total + "Trains Available")
                        val trains = tvalues.trains
                        for (x in trains!!) {
                            list.add("Train Number:" + x.number)
                            list.add("Train Name:" + x.name)
                            list.add(" ")
                        }
                        val myadapter = ArrayAdapter(this@MainActivity, R.layout.myownstyle, list)
                        resultlistview.adapter = myadapter
                        val helper = Helper()
                        helper.getListViewSize(resultlistview)
                    }
                })
            }
        }
    }
    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        showdate(arg1, arg2 + 1, arg3)
    }
    private fun createdDialog(i: Int): Dialog? {
        return if (i == 999) {
            DatePickerDialog(this,myDateListener, year, month, date)
        } else
            return  null
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings ->{
                mAuth!!.signOut()
                val z=Intent(this,LoginActivity::class.java)
                startActivity(z)
            return true}

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.TrainInfo -> {
               val j=Intent(this,TrainInfo::class.java)
                startActivity(j)
            }
            R.id.TrainName -> {
                val i=Intent(this,TrainName::class.java)
                startActivity(i)
            }
            R.id.PNRStatus -> {
                val i=Intent(this,PnrStatusDetails::class.java)
                startActivity(i)
            }
            R.id.CancelledTrains -> {
                val x=Intent(this,CanTrainsClass::class.java)
                startActivity(x)
            }

            R.id.nav_share -> {
                val kk=Intent(this,SrcDestination::class.java)
                startActivity(kk)
            }
            R.id.trainarrivals ->
            {
                val z=Intent(this,TrainArrivalDetails::class.java)
                startActivity(z)
            }
            R.id.nav_signout-> {
                mAuth!!.signOut()
                val z=Intent(this,LoginActivity::class.java)
                startActivity(z)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
