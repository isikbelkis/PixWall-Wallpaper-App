package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.R
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.network.UnsplashService
import com.example.backgroundapp.util.Constants
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

    private val context = getApplication<Application>().applicationContext

    val imageResponse: MutableLiveData<PhotoItem> = MutableLiveData()
    private val isPhotoSaved = MutableLiveData<Boolean>()
    val isPhotoSavedLiveData: LiveData<Boolean> get() = isPhotoSaved

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    val isLoading = MutableLiveData(false)

    fun getImageDetail(id: String) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val detailResponse = unsplashService.getImageDetail(id = id)
                if (detailResponse.isSuccessful) {
                    imageResponse.postValue(detailResponse.body())
                }
            } catch (e: Exception) {
                errorMessage.value = context.getString(R.string.error_saving_user_info)
            } finally {
                isLoading.value = false
            }
        }
    }


    fun saveImageProfile(photoItem: PhotoItem) {
        val userId = auth.currentUser?.uid ?: return
        val photoRef = firestore.collection(Constants.USERS).document(userId)
            .collection(Constants.SAVED_PHOTOS)
        isLoading.value = true
        viewModelScope.launch {
            try {
                val photoData = hashMapOf(
                    Constants.ID to photoItem.id,
                    Constants.URL to photoItem.urls?.full,
                    Constants.USER to photoItem.user?.name,
                    Constants.PROFILE_IMAGE to photoItem.user?.profileImage?.small
                )
                photoRef.document(photoItem.id!!).set(photoData).await()
                Log.d("DetailViewModel", "kaydedildi.")
                isPhotoSaved.postValue(true)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "kaydedilmedi ${e.localizedMessage}")
                isPhotoSaved.postValue(false)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun checkIfPhotoIsSaved(photoUrl: String) {
        val userId = auth.currentUser?.uid ?: return
        val savedPhotosRef = firestore.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.SAVED_PHOTOS)
            .whereEqualTo(Constants.URL, photoUrl)

        viewModelScope.launch {
            val documents = savedPhotosRef.get().await()
            isPhotoSaved.postValue(!documents.isEmpty)
        }
    }
}
