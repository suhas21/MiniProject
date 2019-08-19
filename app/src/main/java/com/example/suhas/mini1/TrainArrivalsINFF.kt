package com.example.suhas.mini1

import com.example.suhas.mini1.Train_Arrivals.TrainArrivalsPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrainArrivalsINFF {
    @GET("v2/arrivals/station/{value}/hours/4/apikey/91umr8ozt9/")
    fun getInfo(@Path("value") value:String):Call<TrainArrivalsPOJO>
}