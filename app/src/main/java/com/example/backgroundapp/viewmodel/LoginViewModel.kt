package com.example.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backgroundapp.R
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(
    application: Application,
    private val auth: FirebaseAuth
) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResultLiveData: LiveData<Boolean> get() = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = _errorMessage

    private val context = getApplication<Application>().applicationContext

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = context.getString(R.string.error_empty_fields)
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.value = true
                } else {
                    _loginResult.value = false
                    _errorMessage.value = task.exception?.message ?: context.getString(R.string.error_login_failed)
                }
            }
        }
    }
}


