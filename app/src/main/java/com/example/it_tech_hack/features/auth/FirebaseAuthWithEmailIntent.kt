package com.example.hz.features.firebase.firebaseAuthWithEmail.models

import com.example.hz.common.BaseIntent

sealed interface FirebaseAuthWithEmailIntent: BaseIntent {
    data class ChangeEmail(val email: String): FirebaseAuthWithEmailIntent
    data class ChangePassword(val password: String): FirebaseAuthWithEmailIntent
    data object Auth: FirebaseAuthWithEmailIntent
    data object SignIn: FirebaseAuthWithEmailIntent
}