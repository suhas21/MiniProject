package com.example.suhas.mini1

import com.example.suhas.mini1.CanTrains.CanTrainPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CanTrainsINFF {
    @GET("v2/cancelled/date/{date}/apikey/91umr8ozt9/")
    fun loadTotalData(@Path("date") s:String):Call<CanTrainPOJO>
}