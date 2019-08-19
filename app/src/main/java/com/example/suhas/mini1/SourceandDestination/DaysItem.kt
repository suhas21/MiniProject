package com.example.suhas.mini1.SourceandDestination

import com.google.gson.annotations.SerializedName

data class DaysItem(@SerializedName("code")
                    val code: String = "",
                    @SerializedName("runs")
                    val runs: String = "")