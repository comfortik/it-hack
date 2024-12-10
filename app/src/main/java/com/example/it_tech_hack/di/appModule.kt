package com.example.it_tech_hack.di

import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel
import com.example.it_tech_hack.data.repositoryImpl.AuthRepositoryImpl
import com.example.it_tech_hack.domain.repositories.AuthRepository
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.it_tech_hack.features.mainScreen.MainScreenViewModel
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {
    single<FirebaseAuth>{FirebaseAuth.getInstance()}

    single<SupabaseClient>{
        createSupabaseClient(
            supabaseUrl = "https://azookfpubmknqjmqmpft.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpX" +
                    "VCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF6b29rZnB1" +
                    "Ym1rbnFqbXFtcGZ0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzM4NDE1NDIsImV4" +
                    "cCI6MjA0OTQxNzU0Mn0.h5qd2bva0bLV87vidXQazDVLzzKj_bYsN45-cVYmfH8"
        ){
            install(Postgrest)
            install(Realtime)

            defaultSerializer = KotlinXSerializer(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
    single<FirebaseFirestore>{FirebaseFirestore.getInstance()}
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::FirebaseAuthWithEmailViewModel)
}