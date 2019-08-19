package com.example.suhas.mini1.CanTrains

import com.google.gson.annotations.SerializedName

data class TrainsItem(@SerializedName("start_time")
                      val startTime: String = "",
                      @SerializedName("number")
                      val number: String = "",
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("source")
                      val source: Source,
                      @SerializedName("dest")
                      val dest: Dest,
                      @SerializedName("type")
                      val type:String = null.toString()
)