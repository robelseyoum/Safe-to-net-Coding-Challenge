package com.robelseyoum3.safetonet.model


import com.google.gson.annotations.SerializedName

data class Diameter(
    @SerializedName("feet")
    val feet: Double,
    @SerializedName("meters")
    val meters: Double
)