package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.network.UnsplashService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailViewModel(
    application: Application,
    private val unsplashService: UnsplashService,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : AndroidViewModel(application) {

    val imageResponse: MutableLiveData<PhotoItem> = MutableLiveData()
    private val isPhotoSaved = MutableLiveData<Boolean>()
    val isPhotoSavedLiveData: LiveData<Boolean> get() = isPhotoSaved

    fun getImageDetail(id: String) {
        viewModelScope.launch {
            val detailResponse = unsplashService.getImageDetail(id = id)
            if (detailResponse.isSuccessful) {
                imageResponse.postValue(detailResponse.body())
            }
        }
    }

    fun saveImageProfile(photoItem: PhotoItem) {
        val userId = auth.currentUser?.uid ?: return
        val photoRef = firestore.collection("users").document(userId).collection("saved_photos")
        viewModelScope.launch {
            try {
                val photoData = hashMapOf(
                    "id" to photoItem.id,
                    "url" to photoItem.urls?.full,
                    "user" to photoItem.user?.name
                )
                photoRef.document(photoItem.id!!).set(photoData).await()
                Log.d("DetailViewModel", "kaydedildi.")
                isPhotoSaved.postValue(true)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "kaydedilmedi ${e.localizedMessage}")
                isPhotoSaved.postValue(false)
            }
        }
    }

    fun checkIfPhotoIsSaved(photoUrl: String) {
        val userId = auth.currentUser?.uid ?: return
        val savedPhotosRef = firestore.collection("users")
            .document(userId)
            .collection("saved_photos")
            .whereEqualTo("url", photoUrl)

        viewModelScope.launch {
            val documents = savedPhotosRef.get().await()
            isPhotoSaved.postValue(!documents.isEmpty)
        }
    }
}
