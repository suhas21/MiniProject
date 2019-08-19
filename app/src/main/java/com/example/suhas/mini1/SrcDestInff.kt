package com.example.suhas.mini1

import com.example.suhas.mini1.SourceandDestination.SourceDestinationPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
//source==PBH
interface SrcDestInff {
    //jkh08wrru2
    @GET("v2/between/source/{svalue}/dest/{destvalue}/date/{dvalue}/apikey/91umr8ozt9/")
    fun getDetails(@Path("svalue") s:String,@Path("destvalue") k:String,@Path("dvalue") l:String):Call<SourceDestinationPOJO>

}