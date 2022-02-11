package com.example.nycschoolstest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolstest.repository.Repository
import com.example.nycschoolstest.repository.models.NycSchool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(private val repo: Repository): ViewModel() {

    private var _schoolsLiveData:MutableLiveData<List<NycSchool>> = MutableLiveData()
    val schoolsLiveData = _schoolsLiveData

    fun getSchools(){
        viewModelScope.launch(Dispatchers.IO) {
            _schoolsLiveData.postValue(repo.getSchools())
        }
    }
}