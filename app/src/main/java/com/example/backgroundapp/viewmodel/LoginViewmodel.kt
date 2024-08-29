package com.example.backgroundapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewmodel : ViewModel() {

    private lateinit var auth: FirebaseAuth

    private val loginResult = MutableLiveData<Boolean>()
    val loginResultLiveData: LiveData<Boolean> get() = loginResult

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    init {
        auth = FirebaseAuth.getInstance()
    }


    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            errorMessage.value = "Please fill in all fields."
        }else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginResult.value = true
                }else{
                    loginResult.value=false
                    errorMessage.value=task.exception?.message ?: "Login Failed."
                }
            }
        }
    }
}

