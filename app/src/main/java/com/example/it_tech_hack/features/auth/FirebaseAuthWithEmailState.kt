package com.example.hz.features.firebase.firebaseAuthWithEmail.models

import com.example.hz.common.BaseState

data class FirebaseAuthWithEmailState (
    val email: String="",
    val password: String="",
    val isLoading: Boolean = false
): BaseState