package com.example.suhas.mini1.TrainRoute

import com.google.gson.annotations.SerializedName

data class TrainRoutePOJO(@SerializedName("response_code")
                         val responseCode: Int = 0,
                         @SerializedName("route")
                         val route: List<RouteItem>?,
                         @SerializedName("debit")
                         val debit: Int = 0,
                         @SerializedName("train")
                         val train: Train
)