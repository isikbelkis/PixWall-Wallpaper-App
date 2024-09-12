package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.R
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.model.Urls
import com.example.backgroundapp.model.User
import com.example.backgroundapp.model.UserProfileImage
import com.example.backgroundapp.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel(
    application: Application,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val userData = MutableLiveData<Map<String, String>>()
    val userDataLiveData: LiveData<Map<String, String>> get() = userData

    private val savedPhotos = MutableLiveData<List<PhotoItem>>()
    val savedPhotosLiveData: LiveData<List<PhotoItem>> get() = savedPhotos

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    val isLoading = MutableLiveData(false)


    fun getUserData() {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            firestore.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userMap = mapOf(
                            Constants.FIRSTNAME to (document.getString(Constants.FIRSTNAME) ?: ""),
                            Constants.LASTNAME to (document.getString(Constants.LASTNAME) ?: ""),
                            Constants.USERNAME to (document.getString(Constants.USERNAME) ?: ""),
                            Constants.EMAIL to (document.getString(Constants.EMAIL) ?: "")
                        )
                        userData.value = userMap
                    } else {
                        errorMessage.value = context.getString(R.string.error_data_not_found)
                    }
                }
                .addOnFailureListener {
                    errorMessage.value = context.getString(R.string.error_fetch_user_data)
                }
        } else {
            errorMessage.value = context.getString(R.string.error_user_not_logged)
        }
    }

    fun getSavedPhotos() {
        val userId = auth.currentUser?.uid ?: return
        val photoRef = firestore.collection("users").document(userId).collection("saved_photos")
        isLoading.value=true
        viewModelScope.launch {
            try {
                val snapshot = photoRef.get().await()
                val photos = snapshot.documents.map { doc ->
                    PhotoItem(
                        id = doc.getString(Constants.ID) ?: "Unknown ID",
                        description = "",
                        likes = 0,
                        urls = Urls(
                            full = doc.getString(Constants.URL) ?: "",
                            regular = ""
                        ),
                        user = User(
                            name = doc.getString(Constants.USER) ?: "Unknown User",
                            lastName = "",
                            id = "",
                            profileImage = UserProfileImage(
                                small = doc.getString(Constants.PROFILE_IMAGE) ?: ""
                            )
                        )
                    )
                }
                if (photos.isNotEmpty()) {
                    Log.d("DetailViewModel", "${photos.size} fotoğraf alındı.")
                } else {
                    Log.d("DetailViewModel", "fotoğraf listesi boş.")
                }

                savedPhotos.postValue(photos)
            } catch (e: Exception) {
                errorMessage.value = context.getString(R.string.error_saving_user_info)
            } finally {
                isLoading.value = false
            }
        }
    }
}
