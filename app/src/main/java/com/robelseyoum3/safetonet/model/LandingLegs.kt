package com.robelseyoum3.safetonet.model


import com.google.gson.annotations.SerializedName

data class LandingLegs(
    @SerializedName("material")
    val material: Any,
    @SerializedName("number")
    val number: Int
)