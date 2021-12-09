package com.example.nycschoolstest.repository

import com.example.nycschoolstest.repository.models.NycSchool
import retrofit2.http.GET

interface NycSchoolsApi {

    @GET("s3k6-pzi2.json")
    suspend fun getSchools(): List<NycSchool>

    @GET("f9bf-2cp4.json")
    suspend fun getSats()

}