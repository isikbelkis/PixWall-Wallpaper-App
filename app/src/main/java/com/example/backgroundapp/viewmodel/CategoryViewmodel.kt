package com.example.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.AppDatabase
import com.example.backgroundapp.model.Category
import kotlinx.coroutines.launch

class CategoryViewmodel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val categoryDao = database.categoryDao()

    fun addCategory(category: Category) = viewModelScope.launch {
        categoryDao.insertCategory(category)
    }
}