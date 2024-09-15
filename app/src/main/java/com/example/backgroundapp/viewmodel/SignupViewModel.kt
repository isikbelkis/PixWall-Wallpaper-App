package com.example.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.backgroundapp.R
import com.example.backgroundapp.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel(
    application: Application,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AndroidViewModel(application) {

    private val _signupResult = MutableLiveData<Boolean>()
    val signupResultLiveData: LiveData<Boolean> get() = _signupResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = _errorMessage

    private val context = getApplication<Application>().applicationContext

    fun signUp(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String,
    ) {
        when {
            email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() -> {
                _errorMessage.value = context.getString(R.string.error_empty_fields)
            }

            !isValidEmail(email) -> {
                _errorMessage.value = context.getString(R.string.error_invalid_email)
            }

            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            val user = hashMapOf(
                                Constants.FIRSTNAME to firstName,
                                Constants.LASTNAME to lastName,
                                Constants.USERNAME to username,
                                Constants.EMAIL to email
                            )

                            userId?.let {
                                firestore.collection(Constants.USERS).document(it)
                                    .set(user)
                                    .addOnSuccessListener {
                                        _signupResult.value = true
                                    }
                                    .addOnFailureListener { e ->
                                        _errorMessage.value = context.getString(
                                            R.string.error_saving_user_info,
                                            e.localizedMessage
                                        )
                                    }
                            }
                        } else {
                            _errorMessage.value = context.getString(
                                R.string.error_registration_failed,
                                task.exception?.message
                            )
                        }
                    }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}