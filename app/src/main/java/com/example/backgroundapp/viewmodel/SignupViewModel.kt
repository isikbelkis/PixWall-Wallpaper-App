package com.example.backgroundapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel(
    application: Application,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AndroidViewModel(application) {

    private val signupResult = MutableLiveData<Boolean>()
    val signupResultLiveData: LiveData<Boolean> get() = signupResult

    private val errorMessage = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessage

    fun signUp(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String,
    ){
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            errorMessage.value = "Please fill in all fields."
        } else if (!isValidEmail(email)) {
            errorMessage.value = "Invalid email format."
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "username" to username,
                        "email" to email
                    )

                    if (userId != null) {
                        firestore.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                signupResult.value = true
                            }
                            .addOnFailureListener {
                                errorMessage.value = "Error saving user information"
                            }
                    }
                } else {
                    errorMessage.value = task.exception?.message ?: "Registration Failed"
                }
            }
        }
    }
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}