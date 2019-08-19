package com.example.suhas.mini1.TrainRoute

import com.google.gson.annotations.SerializedName

data class RouteItem(@SerializedName("halt")
                     val halt: Int = 0,
                     @SerializedName("no")
                     val no: Int = 0,
                     @SerializedName("distance")
                     val distance: Int = 0,
                     @SerializedName("schdep")
                     val schdep: String = "",
                     @SerializedName("station")
                     val station: Station,
                     @SerializedName("scharr")
                     val scharr: String = "",
                     @SerializedName("day")
                     val day: Int = 0)