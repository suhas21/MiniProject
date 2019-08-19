package com.example.suhas.mini1.TrainName_Number

import com.google.gson.annotations.SerializedName

data class ClassesItem(@SerializedName("code")
                       val code: String = "",
                       @SerializedName("available")
                       val available: String = "",
                       @SerializedName("name")
                       val name: String = "")