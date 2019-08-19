package com.example.suhas.mini1

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.Train_Arrivals.TrainArrivalsPOJO
import kotlinx.android.synthetic.main.activity_train_arrival_details.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrainArrivalDetails : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_arrival_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@TrainArrivalDetails)) {
        } else {
            var sbar= Snackbar.make(arrivalslayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    fun getDetailedInfo(v:View)
    {
        if (arrivalset1.text.toString().isEmpty()) {
            arrivalset1.error = "This Field Should not be Empty"
        }
        else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},800)
            val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.railwayapi.com/").build()
            val a3 = r.create(TrainArrivalsINFF::class.java)
            val z = arrivalset1.text.toString()
            val call: Call<TrainArrivalsPOJO> = a3.getInfo(z)
            call.enqueue(object : retrofit2.Callback<TrainArrivalsPOJO> {
                override fun onFailure(call: Call<TrainArrivalsPOJO>, t: Throwable) {
                    if (cmm.isConnectingToInternet(this@TrainArrivalDetails)) {
                        var sbar= Snackbar.make(arrivalslayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                    } else {
                        var sbar= Snackbar.make(arrivalslayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onResponse(call: Call<TrainArrivalsPOJO>, response: Response<TrainArrivalsPOJO>) {
                    Toast.makeText(this@TrainArrivalDetails, "Success", Toast.LENGTH_SHORT).show()
                    val list = mutableListOf<String>()
                    val tname = response.body()
                    val t = tname!!.trains
                    for (x in t!!)
                    {
                        list.add("Train Name:"+x.name)
                        list.add("Train Number:"+x.number)
                        list.add("Arrival Time:"+x.scharr)
                        list.add("Depature Time:"+x.schdep)
                        list.add(" ")
                    }
                    val myadapter = ArrayAdapter(this@TrainArrivalDetails, R.layout.myownstyle, list)
                    arrivalsview.adapter = myadapter
                    val helper = Helper()
                    helper.getListViewSize(arrivalsview)
                }
            })
        }
    }
}
