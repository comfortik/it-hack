package com.example.it_tech_hack.data.sources

import android.content.Context
import android.content.SharedPreferences

object SharedPrefsProvider {
    lateinit var sharedPrefs : SharedPreferences
    fun init(context: Context){
        sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
   inline fun <reified T> getSharedPrefs(id: String): T? {
        return when (T::class) {
            String::class -> sharedPrefs.getString(id, null) as T?
            Int::class -> sharedPrefs.getInt(id, 0) as T
            Boolean::class -> sharedPrefs.getBoolean(id, false) as T
            Float::class -> sharedPrefs.getFloat(id, 0f) as T
            Long::class -> sharedPrefs.getLong(id, 0L) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
    inline fun <reified T> setSharedPrefs(id: String, data: T) {
        with(sharedPrefs.edit()) {
            when (T::class) {
                String::class -> putString(id, data as String)
                Int::class -> putInt(id, data as Int)
                Boolean::class -> putBoolean(id, data as Boolean)
                Float::class -> putFloat(id, data as Float)
                Long::class -> putLong(id, data as Long)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }



}

const val SHARED_PREFS = "SHARED_PREFS"