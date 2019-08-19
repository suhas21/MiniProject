package com.example.suhas.mini1

import com.example.suhas.mini1.TrainName_Number.NameNumberPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface TrainNameINFF {
    @GET("v2/name-number/train/{num}/apikey/91umr8ozt9/")
    fun  getName(@Path("num") num:Int):Call<NameNumberPOJO>
}