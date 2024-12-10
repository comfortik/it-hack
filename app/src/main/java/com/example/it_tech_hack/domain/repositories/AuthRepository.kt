package com.example.it_tech_hack.domain.repositories

import com.google.firebase.auth.FirebaseAuthException

interface AuthRepository {
    suspend fun signUp(email: String, password: String,  onComplete: (String)-> Unit, onError: (e: FirebaseAuthException)->Unit)
    suspend fun signIn(email: String, password: String,  onComplete: (String)-> Unit, onError: (e: FirebaseAuthException)->Unit)
}