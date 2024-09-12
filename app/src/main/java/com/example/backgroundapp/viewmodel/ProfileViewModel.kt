package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.model.PhotoItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel(
    application: Application,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AndroidViewModel(application) {

    private val userData = MutableLiveData<Map<String, String>>()
    val userDataLiveData: LiveData<Map<String, String>> get() = userData

    private val savedPhotos = MutableLiveData<List<PhotoItem>>()
    val savedPhotosLiveData: LiveData<List<PhotoItem>> get() = savedPhotos

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    fun getUserData() {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            firestore.collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userMap = mapOf(
                            "firstName" to (document.getString("firstName") ?: ""),
                            "lastName" to (document.getString("lastName") ?: ""),
                            "username" to (document.getString("username") ?: ""),
                            "email" to (document.getString("email") ?: "")
                        )
                        userData.value = userMap
                    } else {
                        errorMessage.value = "User data not found"
                    }
                }
                .addOnFailureListener {
                    errorMessage.value = "Failed to fetch user data"
                }
        } else {
            errorMessage.value = "User not logged in"
        }
    }

    fun getSavedPhotos() {
        val userId = auth.currentUser?.uid ?: return
        val photoRef = firestore.collection("users").document(userId).collection("saved_photos")

        viewModelScope.launch {
            val snapshot = photoRef.get().await()
            val photos = snapshot.documents.map { doc ->
                PhotoItem(
                    id = doc.getString("id") ?: "Unknown ID",
                    description = "",
                    likes = 0,
                    urls = PhotoItem.Urls(
                        full = doc.getString("url") ?: "",
                        regular = ""
                    ),
                    user = PhotoItem.User(
                        name = doc.getString("user") ?: "Unknown User",
                        lastName = "",
                        id = "",
                        profileImage = PhotoItem.User.ProfileImage(
                            small = doc.getString("profile_image") ?: ""
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
        }
    }
}
