package com.example.it_tech_hack.data.repositoryImpl

import android.util.Log
import com.example.it_tech_hack.data.repositoryImpl.UserRepositoryImpl.Companion.USER_ID
import com.example.it_tech_hack.data.sources.SharedPrefsProvider
import com.example.it_tech_hack.domain.models.Stock
import com.example.it_tech_hack.domain.repositories.StockRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.FilterOperation
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.Flow

class StockRepositoryImpl(
    private val supabase: SupabaseClient
): StockRepository {
    val uid: String by lazy { SharedPrefsProvider.getSharedPrefs(USER_ID)?:"uid" }
    @OptIn(SupabaseExperimental::class)
    override suspend fun subscribeBriefcase(): Flow<List<Stock>> =
        supabase.from(STOCK_TABLE_NAME).selectAsFlow(
            primaryKey = Stock::id,
            filter = FilterOperation("uid", FilterOperator.EQ, uid)
        )


    override suspend fun addNewStock(symbol: String, count: Int, type: Int ) {
        Log.d("uid", uid)
        Log.d("a", "$symbol $count $type")
        supabase.from(STOCK_TABLE_NAME).insert(
            Stock(uid = uid,
                type_invest = type,
                count = count,
                symbol = symbol
                )
        )
    }

    override suspend fun updateCountOfSymbol(symbol: String, count: Int) {

    }

    companion object{
        const val STOCK_TABLE_NAME = "stocks"
    }
}