package com.example.hz.features.firebase.firebaseAuthWithEmail.models

import com.example.hz.common.BaseAction

sealed interface FirebaseAuthWithEmailAction: BaseAction {
    data class ShowError(val message: String): FirebaseAuthWithEmailAction
    data class NavigateToProfile(val user: String): FirebaseAuthWithEmailAction
    data object SignIn: FirebaseAuthWithEmailAction
}