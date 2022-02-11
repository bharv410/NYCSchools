package com.example.nycschoolstest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nycschoolstest.repository.Repository

class ItemDetailViewModelProvider(private val repo: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemDetailViewModel(repo) as T
    }
}