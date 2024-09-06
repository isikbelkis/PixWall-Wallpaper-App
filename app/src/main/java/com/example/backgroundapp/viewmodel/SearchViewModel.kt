package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.network.UnsplashService
import com.example.backgroundapp.util.Constants
import kotlinx.coroutines.launch

class SearchViewModel(
    application: Application,
    private val unsplashService: UnsplashService
) : AndroidViewModel(application) {

    private val searchResults = MutableLiveData<List<PhotoItem>>()
    val searchResultsLiveData: LiveData<List<PhotoItem>> get() = searchResults

    fun searchPhotos(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                val response = unsplashService.searchPhotos(
                    keyword = query,
                    apiKey = Constants.API_KEY,
                    page = 1,
                    perPage = 30
                )
                if (response.isSuccessful) {
                    response.body()?.results?.let { photos ->
                        searchResults.postValue(photos)
                    }
                } else {
                    searchResults.postValue(emptyList())
                }
            }
        }
    }
}
