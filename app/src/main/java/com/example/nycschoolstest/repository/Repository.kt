package com.example.nycschoolstest.repository

import com.example.nycschoolstest.repository.models.NycSchool

class Repository {

    suspend fun getSchools(): List<NycSchool>{
        return RetrofitInstance.nycSchoolsApi.getSchools()
    }
}