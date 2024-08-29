package com.example.backgroundapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignupViewmodel : ViewModel() {

    private lateinit var auth: FirebaseAuth

    private val signupResult = MutableLiveData<Boolean>()
    val signupResultLiveData: LiveData<Boolean> get() = signupResult

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    init {
        auth = FirebaseAuth.getInstance()
    }

    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.value = "Please fill in all fields."
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signupResult.value = true
                } else {
                    errorMessage.value = task.exception?.message ?: "Registration Failed"
                }
            }
        }
    }
}
