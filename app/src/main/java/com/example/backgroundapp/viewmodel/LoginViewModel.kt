package com.example.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(
    application: Application,
    private val auth: FirebaseAuth
) : AndroidViewModel(application) {

    private val loginResult = MutableLiveData<Boolean>()
    val loginResultLiveData: LiveData<Boolean> get() = loginResult

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.value = "Please fill in all fields."
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginResult.value = true
                } else {
                    loginResult.value = false
                    errorMessage.value = task.exception?.message ?: "Login Failed."
                }
            }
        }
    }
}


