package com.madeit.supermarionetcall.data.model

import com.google.gson.annotations.SerializedName


data class Mario (
    val id: Int,
    @SerializedName("amiiboSeries")
    val marioSeries: String?,
    val character: String?,
    val gameSeries: String?,
    val head: String?,
    val image: String?,
    val name: String?,
    val release: Release?,
    val tail: String?,
    val type: String
)