package com.example.actifit.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResultModel(

    @SerializedName("resultCount") val resultCount: Int,

    @SerializedName("results") val results: List<SongModel>
)