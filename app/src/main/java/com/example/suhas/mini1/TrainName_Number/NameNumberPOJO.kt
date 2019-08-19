package com.example.suhas.mini1.TrainName_Number

import com.google.gson.annotations.SerializedName

data class NameNumberPOJO(@SerializedName("response_code")
                          val responseCode: Int = 0,
                          @SerializedName("debit")
                          val debit: Int = 0,
                          @SerializedName("train")
                          val train: Train)