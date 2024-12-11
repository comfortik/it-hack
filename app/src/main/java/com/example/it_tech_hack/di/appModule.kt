package com.example.it_tech_hack.di

import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel
import com.example.it_tech_hack.data.repositoryImpl.AuthRepositoryImpl
import com.example.it_tech_hack.data.repositoryImpl.BondsRepositoryImpl
import com.example.it_tech_hack.data.repositoryImpl.CurrencyRepositoryImpl
import com.example.it_tech_hack.data.repositoryImpl.StockRemoteRepositoryImpl
import com.example.it_tech_hack.data.repositoryImpl.StockRepositoryImpl
import com.example.it_tech_hack.data.repositoryImpl.UserRepositoryImpl
import com.example.it_tech_hack.domain.repositories.AuthRepository
import com.example.it_tech_hack.domain.repositories.BondsRepository
import com.example.it_tech_hack.domain.repositories.CurrencyRepository
import com.example.it_tech_hack.domain.repositories.StockRemoteRepository
import com.example.it_tech_hack.domain.repositories.StockRepository
import com.example.it_tech_hack.domain.repositories.UserRepository
import com.example.it_tech_hack.domain.useCases.GetBriefcaseCostUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.it_tech_hack.features.mainScreen.MainScreenViewModel
import com.example.it_tech_hack.features.market.MarketViewModel
import com.example.it_tech_hack.features.profile.ProfileScreenViewModel
import org.koin.core.qualifier.named
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
    single<GetBriefcaseCostUseCase>{GetBriefcaseCostUseCase(get(), get())}
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<StockRepository> { StockRepositoryImpl(get()) }
    single<StockRemoteRepository> { StockRemoteRepositoryImpl(get(named("StockApi"))) }
    single<BondsRepository> { BondsRepositoryImpl(get(named("StockApi"))) }

    single<CurrencyRepository> {CurrencyRepositoryImpl(get(named("CurrencyApi"))) }

    viewModelOf(::MainScreenViewModel)
    viewModelOf(::ProfileScreenViewModel)
    viewModelOf(::MarketViewModel)
    viewModelOf(::FirebaseAuthWithEmailViewModel)
}
