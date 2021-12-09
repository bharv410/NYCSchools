package com.example.nycschoolstest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.example.nycschoolstest.repository.Repository

class ItemViewModelProvider(private val repo: Repository): Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemViewModel(repo) as T
    }
}