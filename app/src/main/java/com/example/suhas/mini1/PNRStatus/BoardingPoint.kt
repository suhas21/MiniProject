package com.example.suhas.mini1.PNRStatus

import com.google.gson.annotations.SerializedName

data class BoardingPoint(@SerializedName("code")
                         val code: String = "",
                         @SerializedName("name")
                         val name: String = "")