package com.example.suhas.mini1

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.TrainName_Number.NameNumberPOJO
import kotlinx.android.synthetic.main.activity_train_name.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class TrainName : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_name)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@TrainName)) {
        } else {
            var sbar= Snackbar.make(trainnamelayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
    fun loadName(v:View) {
        if (nameet1.text.toString().isEmpty()) {
            nameet1.error = "This Field Should not be Empty"
        } else if ((nameet1.text.toString().length > 5) || (nameet1.text.toString().length < 5)) {
            nameet1.error = "Enter a Valid 5 digit Train Number"
        } else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},800)
            val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.railwayapi.com/").build()
            val a3 = r.create(TrainNameINFF::class.java)
            val z = nameet1.text.toString().toInt()
            val call: Call<NameNumberPOJO> = a3.getName(z)

            call.enqueue(object : retrofit2.Callback<NameNumberPOJO> {
                override fun onFailure(call: Call<NameNumberPOJO>, t: Throwable) {
                    if (cmm.isConnectingToInternet(this@TrainName)) {
                        var sbar= Snackbar.make(trainnamelayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                                          } else {
                        var sbar= Snackbar.make(trainnamelayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onResponse(call: Call<NameNumberPOJO>, response: Response<NameNumberPOJO>) {
                    Toast.makeText(this@TrainName, "Success", Toast.LENGTH_SHORT).show()
                    val list = mutableListOf<String>()
                    val tname = response.body()
                    list.add("Train Name:" + tname!!.train.name)
                    list.add("Train Num:" + tname.train.number)
                    val days = tname.train.days
                    val seats = tname.train.classes
                    list.add("Running Days")
                    for (x in days!!) {
                        list.add("DAY:" + x.code)
                        list.add("RUNS:" + x.runs)
                        list.add(" ")
                    }
                    list.add("Types of Seats Available")
                    for (x in seats!!) {
                        list.add("Name:" + x.name)
                        list.add("Code:" + x.code)
                        list.add("Available:" + x.available)
                        list.add(" ")
                    }
                    val myadapter = ArrayAdapter(this@TrainName, R.layout.myownstyle, list)
                    namelview.adapter = myadapter
                    val helper = Helper()
                    helper.getListViewSize(namelview)
                }
            })
        }
    }
}
