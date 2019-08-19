package com.example.suhas.mini1
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.suhas.mini1.SourceandDestination.SourceDestinationPOJO
import kotlinx.android.synthetic.main.activity_src_destination.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Handler
import android.support.design.widget.Snackbar

class SrcDestination : AppCompatActivity() {
    val cmm=ConnectivityHelper()
    lateinit var pbar: ProgressDialog
    private var year: Int = 0
    private var month: Int = 0
    private var date: Int = 0
    private lateinit var  calendar: Calendar
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_src_destination)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        calendar= Calendar.getInstance()
        year=calendar.get(Calendar.YEAR)
        month=calendar.get(Calendar.MONTH)
        date=calendar.get(Calendar.DATE)
        showDate(year,month+1,date)
        pbar= ProgressDialog(this)
        pbar.setMessage("Loading")
        pbar.setCancelable(false)
        if (cmm.isConnectingToInternet(this@SrcDestination)) {
        } else {
            var sbar= Snackbar.make(srcdestlayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
        }
    }
   fun SelectDate(v:View)
    {
    createdDialog(999)!!.show()
    }
    protected fun createdDialog(i: Int): Dialog? {
        return if (i == 999) {
            DatePickerDialog(this,myDateListener, year, month, date)
        } else
            return  null
    }
    private val myDateListener = DatePickerDialog.OnDateSetListener { arg0, arg1, arg2, arg3 ->
        showDate(arg1, arg2 + 1, arg3)
    }
    private fun showDate(y:Int, m:Int, d:Int)
    {
        val s:StringBuilder=StringBuilder().append(d).append("-").append(m).append("-").append(y)
        datepick.text=s.toString()
    }
    fun loadData(v: View) {
        val svalue = sourceet1.text.toString()
        val destvalue = destet2.text.toString()
        val datevalue = datepick.text.toString()
        if (svalue.isEmpty())
        {
            sourceet1.error="This Field Should Not be Empty"
        }
        else if(destvalue.isEmpty())
        {
            destet2.error="This Field Should Not be Empty"
        }
        else {
            pbar.show()
            Handler().postDelayed({pbar.dismiss()},800)
            val r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.railwayapi.com/").build()
            val a5 = r.create(SrcDestInff::class.java)
            val call5 = a5.getDetails(svalue, destvalue, datevalue)
            call5.enqueue(object : retrofit2.Callback<SourceDestinationPOJO> {
                override fun onFailure(call: Call<SourceDestinationPOJO>, t: Throwable) {
                    if (cmm.isConnectingToInternet(this@SrcDestination)) {
                        var sbar= Snackbar.make(srcdestlayout,"Failed In Getting Details", Snackbar.LENGTH_LONG).show()
                        //Toast.makeText(this@SrcDestination, "Failed", Toast.LENGTH_SHORT).show()
                    } else {
                        var sbar= Snackbar.make(srcdestlayout,"Requires Internet Connection", Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onResponse(call: Call<SourceDestinationPOJO>, response: Response<SourceDestinationPOJO>) {
                    Toast.makeText(this@SrcDestination, "Success", Toast.LENGTH_SHORT).show()
                    val list = mutableListOf<String>()
                    val tvalues = response.body()
                    list.add("Total " + tvalues!!.total + " Available")
                    val trains = tvalues.trains
                    for (x in trains!!) {
                        list.add("Train Number:" + x.number)
                        list.add("Train Name:" + x.name)
                        list.add(" ")
                    }
                    val myadapter = ArrayAdapter(this@SrcDestination, R.layout.myownstyle, list)
                    srclview.adapter = myadapter
                    val helper = Helper()
                    helper.getListViewSize(srclview)
                }
            })
        }
    }
}
