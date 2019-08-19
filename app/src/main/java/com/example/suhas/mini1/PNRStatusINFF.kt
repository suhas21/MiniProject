package com.example.suhas.mini1

import com.example.suhas.mini1.PNRStatus.PNRStatusPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//8436858347
interface PNRStatusINFF {
    @GET("v2/pnr-status/pnr/{pvalue}/apikey/91umr8ozt9/")
    fun getData(@Path("pvalue") pvalue:Int):Call<PNRStatusPOJO>
}