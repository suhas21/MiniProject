package com.example.suhas.mini1

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.PNRStatus.PNRStatusPOJO
import kotlinx.android.synthetic.main.activity_pnr_status_details.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class PnrStatusDetails : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pnr_status_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@PnrStatusDetails)) {
        } else {
            var sbar= Snackbar.make(pnrstatuslayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    fun loadValues(v:View)
    {
        val p=pnret1.text.toString().toInt()
        if(pnret1.text.toString().isEmpty())
        {
            pnret1.error="This Field Should Not be Empty"
        }
        else if ((pnret1.text.toString().length >10)|| (pnret1.text.toString().length<10))
        {
            pnret1.error="Enter a Valid 10 digit PNR Number"
        }
        else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},800)
            val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.railwayapi.com/").build()
            val a6 = r.create(PNRStatusINFF::class.java)
            val api = a6.getData(p)
            api.enqueue(object : retrofit2.Callback<PNRStatusPOJO> {
                override fun onFailure(call: Call<PNRStatusPOJO>, t: Throwable) {
                    if (cmm.isConnectingToInternet(this@PnrStatusDetails)) {
                        var sbar= Snackbar.make(pnrstatuslayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                    } else {
                        var sbar= Snackbar.make(pnrstatuslayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onResponse(call: Call<PNRStatusPOJO>, response: Response<PNRStatusPOJO>) {
                    Toast.makeText(this@PnrStatusDetails, "Success", Toast.LENGTH_SHORT).show()
                    val tname = response.body()
                    val list = mutableListOf<String>()
                    list.add("PNR number:" + tname!!.pnr)
                    list.add("Train Number:" + tname.train.number)
                    list.add("Train Name:" + tname.train.name)
                    list.add("Date of Journey:" + tname.doj)
                    list.add("Total Passengers:" + tname.passengers)
                    list.add("Chat Prepared:" + tname.chartPrepared)
                    list.add("Boarding Point:" + tname.boardingPoint.name)
                    list.add("Reservation Upto:" + tname.reservationUpto.name)
                    list.add("Journey Class:" + tname.journeyClass.name)
                    list.add("Journey Class Code:" + tname.journeyClass.code)
                    list.add("Passenger Details")
                    val de = tname.passengers
                    for (x in de!!) {
                        list.add("Number:" + x.no)
                        list.add("Current Status:" + x.currentStatus)
                        list.add("Booking Status:" + x.bookingStatus)
                        list.add(" ")
                    }
                    val myadapter = ArrayAdapter(this@PnrStatusDetails, R.layout.myownstyle, list)
                    pnrlview.adapter = myadapter
                }
            })
        }
    }
}
