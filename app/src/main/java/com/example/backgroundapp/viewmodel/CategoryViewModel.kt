package com.example.backgroundapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.backgroundapp.model.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    application: Application
) : AndroidViewModel(application) {

    fun addCategory(category: Category) = viewModelScope.launch {
        saveCategoryFirestore(category)
    }

    private fun saveCategoryFirestore(category: Category) {
        val userId = auth.currentUser?.uid ?: return
        val userCategoriesRef =
            firestore.collection("users").document(userId).collection("categories")

        userCategoriesRef.add(category)
            .addOnSuccessListener {
                Log.d("Firestore", "kaydedildi")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "kaydedilemedi", e)
            }
    }
}



