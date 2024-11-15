package com.madeit.supermarionetcall.data.model

import com.google.gson.annotations.SerializedName

data class MarioList(
    @SerializedName("amiibo")
    val marios: List<Mario>
)