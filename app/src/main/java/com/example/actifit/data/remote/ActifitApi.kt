package com.example.actifit.data.remote

import com.example.actifit.data.remote.model.ResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ActifitApi {

    @GET("/search")
    fun getAll(@Query("term") search :String,  @Query("entity")  entity : String, @Query("limit")  limit : Int ) : Call<ResultModel>

}