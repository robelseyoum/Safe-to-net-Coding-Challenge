package com.robelseyoum3.safetonet.model


import com.google.gson.annotations.SerializedName

data class Rockets(

    @SerializedName("active")
    val active: Boolean,
    
    @SerializedName("country")
    val country: String,

    @SerializedName("engines")
    val engines: Engines,

    @SerializedName("rocket_name")
    val rocketName: String
)