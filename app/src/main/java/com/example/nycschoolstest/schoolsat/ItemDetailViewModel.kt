package com.example.nycschoolstest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolstest.repository.Repository
import com.example.nycschoolstest.repository.models.Sat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemDetailViewModel(private val repo: Repository): ViewModel() {

    private var _satsLiveData: MutableLiveData<Sat> = MutableLiveData()
    val satsLiveData = _satsLiveData

    fun getSats(string: String){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getSat(string)
            if(list.size>0)
                _satsLiveData.postValue(repo.getSat(string).get(0))
        }
    }
}