package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.R
import com.example.backgroundapp.model.Category
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.network.UnsplashService
import com.example.backgroundapp.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PhotoViewModel(
    application: Application,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val unsplashService: UnsplashService
) : AndroidViewModel(application) {

    private val _photosLiveData = MutableLiveData<List<PhotoItem>>()
    val photosLiveData: LiveData<List<PhotoItem>> get() = _photosLiveData

    fun loadCategoriesAndFetchPhotos() {
        val userId = auth.currentUser?.uid ?: return
        val categoriesRef = firestore.collection("users").document(userId).collection("categories")

        viewModelScope.launch {
                val documents = categoriesRef.get().await()
                val selectedCategories = documents.mapNotNull { document ->
                    val name = document.getString("name")
                    val id = document.getLong("id")?.toInt()
                    val drawableRes = R.drawable.ic_launcher_background
                    if (name != null && id != null) {
                        Category(id, name, drawableRes, queryParam = name)
                    } else {
                        null
                    }
                }.take(3)

                fetchPhotosForSelectedCategories(selectedCategories)
            }
        }

    private suspend fun fetchPhotosForSelectedCategories(selectedCategories: List<Category>) {
        val allPhotos = mutableListOf<PhotoItem>()

        selectedCategories.forEach { category ->
                val response = unsplashService.categoryPhotos(
                    category = category.queryParam,
                    apiKey = Constants.API_KEY,
                    page = 1,
                    perPage = 30
                )
                if (response.isSuccessful) {
                    response.body()?.results?.let { photos ->
                        allPhotos.addAll(photos)
                    }
                } else {
                    Log.e("API_ERROR", "Error fetching photos for ${category.name}: ${response.errorBody()}")
                }
            }
        _photosLiveData.postValue(allPhotos)
    }
}



