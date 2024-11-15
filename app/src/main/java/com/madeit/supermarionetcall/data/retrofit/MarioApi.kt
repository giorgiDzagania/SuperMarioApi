package com.madeit.supermarionetcall.data.retrofit

import com.madeit.supermarionetcall.data.model.MarioList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarioApi {

    @GET("amiibo/")
    suspend fun getAllMarios(@Query("name") name: String = "mario"): Response<MarioList>

    @GET("amiibo/")
    suspend fun getItemBySearch(@Query("name") name: String): Response<MarioList>


}