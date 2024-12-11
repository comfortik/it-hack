package com.example.it_tech_hack

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel.Companion.USER_ID
import com.example.it_tech_hack.data.sources.SHARED_PREFS
import com.example.it_tech_hack.data.sources.SharedPrefsProvider
import com.example.it_tech_hack.di.appModule
import com.example.it_tech_hack.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, networkModule)
        }
    }
}