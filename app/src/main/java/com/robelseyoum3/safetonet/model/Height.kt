package com.robelseyoum3.safetonet.model


import com.google.gson.annotations.SerializedName

data class Height(
    @SerializedName("feet")
    val feet: Int,
    @SerializedName("meters")
    val meters: Double
)