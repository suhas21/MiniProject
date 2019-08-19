package com.example.suhas.mini1

import com.example.suhas.mini1.TrainRoute.TrainRoutePOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrainRouteInff
{
   @GET("v2/route/train/{trainno}/apikey/91umr8ozt9/")
    fun  getTrainRouteInfo(@Path("trainno") s:Int):Call<TrainRoutePOJO>

}