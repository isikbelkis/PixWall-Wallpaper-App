package com.example.backgroundapp.viewmodel

import android.app.Application
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

    private val searchQuery = MutableLiveData<String>()
    val searchQueryLiveData: LiveData<String> get() = searchQuery

    val isLoading = MutableLiveData(false)

    fun searchPhotos(query: String) {
        isLoading.value = true
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                try {
                    val response = unsplashService.searchPhotos(
                        keyword = query,
                        apiKey = Constants.API_KEY,
                        page = 1,
                        perPage = 30
                    )
                    if (response.isSuccessful) {
                        response.body()?.results?.let { photos ->
                            searchResults.postValue(photos)
                        } ?: searchResults.postValue(emptyList())
                    } else {
                        searchResults.postValue(emptyList())
                    }
                } catch (e: Exception) {
                    searchResults.postValue(emptyList())
                }
                finally {
                    isLoading.value = false
                }
                searchQuery.postValue(query)
            }
        }
    }
}
