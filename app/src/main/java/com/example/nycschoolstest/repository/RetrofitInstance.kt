package com.example.nycschoolstest.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://data.cityofnewyork.us/resource/"

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val nycSchoolsApi:NycSchoolsApi by lazy {
        retrofit.create(NycSchoolsApi::class.java)
    }
}