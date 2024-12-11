package com.example.it_tech_hack.data.repositoryImpl

import android.util.Log
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel
import com.example.hz.features.firebase.firebaseAuthWithEmail.FirebaseAuthWithEmailViewModel.Companion
import com.example.it_tech_hack.data.sources.SharedPrefsProvider
import com.example.it_tech_hack.domain.models.User
import com.example.it_tech_hack.domain.repositories.UserRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserRepositoryImpl(
    val supabase: SupabaseClient
): UserRepository {
    val uid: String by lazy { SharedPrefsProvider.getSharedPrefs(USER_ID)?:"uid" }

    @OptIn(SupabaseExperimental::class)
    override suspend fun getUser(): Flow<User?> {
        Log.d("uid user repo", uid)
       val a = supabase.from(USER_TABLE_NAME).selectAsFlow(
            User::id,
            filter = FilterOperation("id", FilterOperator.EQ, uid)
        ).map { it.firstOrNull() }
        Log.d("user repo", a.toString())
        return a
    }


    override suspend fun addUser(user: User) {
        supabase.from(USER_TABLE_NAME).insert(User(
            id = user.id,
            money = 5.0
        ))
    }

    override suspend fun updateMoney(money: Double) {
        val add = 0.5
        val mutex = Mutex()
        mutex.withLock {
            supabase.from(USER_TABLE_NAME).update({
                set("money", money+add)
            }){
                filter { eq("id", uid) }
            }
        }



    }

    companion object{
        const val USER_TABLE_NAME = "users"
        const val USER_ID = "USER_ID"
    }
}