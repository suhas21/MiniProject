package com.example.suhas.mini1

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.TrainRoute.TrainRoutePOJO
import kotlinx.android.synthetic.main.activity_train_info.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class TrainInfo : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_info)
       supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@TrainInfo)) {
        } else {
            var sbar= Snackbar.make(traininfolayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    fun load(view:View)
    {
        if(traininfo_et1.text.toString().isEmpty())
        {
            traininfo_et1.error="This Field should not be Empty"
        }
        else if ((traininfo_et1.text.toString().length>5 ) || (traininfo_et1.text.toString().length<5))
        {
            traininfo_et1.error="Enter a valid 5 digit Train Number"
        }
        else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},800)
            val v = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.railwayapi.com/").build()
            val a = v.create(TrainRouteInff::class.java)
            val num=traininfo_et1?.text.toString().toInt()
            val c: Call<TrainRoutePOJO> = a.getTrainRouteInfo(num)
            c.enqueue(object : retrofit2.Callback<TrainRoutePOJO> {
                override fun onResponse(call: Call<TrainRoutePOJO>, response: Response<TrainRoutePOJO>) {
                    Toast.makeText(this@TrainInfo, "Successful", Toast.LENGTH_LONG).show()
                    val tname = response.body()
                    val list = mutableListOf<String>()
                    list.add(" ")
                    list.add("Number:" + tname!!.train.number)
                    list.add("Name:" + tname.train.name)
                    val paths = tname.route
                    list.add(" ")
                    for (x in paths!!) {
                        list.add("Station Num:" + x.no)
                        list.add("Station Name:" + x.station.name)
                        list.add("Arrival Time:" + x.scharr)
                        list.add("Halt Time:" + x.halt)
                        list.add("Depature Time:" + x.schdep)
                        list.add(" ")
                    }
                    val myadapter = ArrayAdapter<String>(this@TrainInfo, R.layout.myownstyle, list)
                    traininfo_lview1.adapter = myadapter
                    val helper = Helper()
                    helper.getListViewSize(traininfo_lview1)
                }
                override fun onFailure(call: Call<TrainRoutePOJO>, t: Throwable) {
                    if (cmm.isConnectingToInternet(this@TrainInfo)) {
                        var sbar= Snackbar.make(traininfolayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                    } else {
                        var sbar= Snackbar.make(traininfolayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                    }
                }
            })
        }
    }
}
