package com.example.suhas.mini1

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.icu.util.Calendar
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.CanTrains.CanTrainPOJO
import kotlinx.android.synthetic.main.activity_can_trains_class.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CanTrainsClass : AppCompatActivity() {
    private var year: Int = 0
    private var month: Int = 0
    lateinit var pbar: ProgressDialog
    private  var date: Int = 0
    private  lateinit var sa:StringBuilder
    private lateinit var  calendar: Calendar
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_can_trains_class)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        calendar= Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)
        date=calendar.get(Calendar.DATE)
        showDate(year,month+1,date)
        val cmm=ConnectivityHelper()
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@CanTrainsClass)) {
        } else {
            var sbar= Snackbar.make(canceltrainslayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        showDate(arg1, arg2 + 1, arg3)
    }
    fun ChooseDate(v:View)
    {
        createdDialog(999)!!.show()
    }
    private fun createdDialog(i: Int): Dialog? {
        return if (i == 999) {
            DatePickerDialog(this,myDateListener, year, month, date)
        } else
            return  null
    }
    private fun showDate(y:Int, m:Int, d:Int)
    {
        sa=StringBuilder().append(d).append("-").append(m).append("-").append(y)
        candatepick.text= "$sa"
    }
    fun getInfo(v:View)
    {
        pbar.show()
        Handler().postDelayed({pbar.dismiss()},6000)
        val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.railwayapi.com/").build()
        val a3 = r.create(CanTrainsINFF::class.java)
        val da= "$sa"
        val call: Call<CanTrainPOJO> = a3.loadTotalData(da)
        call.enqueue(object : retrofit2.Callback<CanTrainPOJO> {
            override fun onFailure(call: Call<CanTrainPOJO>, t: Throwable) {
                Toast.makeText(this@CanTrainsClass, "Failed ", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<CanTrainPOJO>, response: Response<CanTrainPOJO>) {

                Toast.makeText(this@CanTrainsClass, "Success", Toast.LENGTH_SHORT).show()
                val list = mutableListOf<String>()
                val tname = response.body()
                list.add("Total Cancelled Train Details are")
                list.add(" ")
                val t = tname!!.trains
                for (x in t!!)
                {
                    list.add("Train Name:"+x.name)
                    list.add("Train Number:"+x.number)
                    list.add("Source Station Name:"+x.source.name)
                    list.add("Source Station Code: "+x.source.code)
                    list.add("Destination Station Name:"+x.dest.name)
                    list.add("Destination Station Code: "+x.dest.code)
                    list.add(" ")
                }
                val myadapter = ArrayAdapter(this@CanTrainsClass, R.layout.myownstyle, list)
                cantrainview.adapter = myadapter
                val helper = Helper()
                helper.getListViewSize(cantrainview)
            }
        })
    }
}
