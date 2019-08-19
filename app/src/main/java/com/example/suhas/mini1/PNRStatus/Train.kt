package com.example.suhas.mini1.PNRStatus

import com.google.gson.annotations.SerializedName

data class Train(@SerializedName("number")
                 val number: String = "",
                 @SerializedName("name")
                 val name: String = "")