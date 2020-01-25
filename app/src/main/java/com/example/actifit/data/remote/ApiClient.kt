package com.example.actifit.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    //Retrofit Call
    companion object Factory {
        private var retrofit: Retrofit? = null
        private val baseUrl = "https://itunes.apple.com"
        fun getClient(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient())
                    .build()
                return retrofit as Retrofit
            }
            return retrofit as Retrofit
        }
    }
}