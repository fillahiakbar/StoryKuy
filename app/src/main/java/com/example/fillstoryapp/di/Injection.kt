package com.example.fillstoryapp.di

import android.content.Context
import com.example.fillstoryapp.data.database.QuoteDatabase
import com.example.fillstoryapp.data.pref.UserPreference
import com.example.fillstoryapp.data.pref.dataStore
import com.example.fillstoryapp.data.repo.UserRepository
import com.example.fillstoryapp.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = QuoteDatabase.getDatabase(context)
        val user = pref.getToken(context)
        val apiService = ApiConfig.ApiService(runBlocking{ user.first() })
        return UserRepository.getInstance(pref, apiService)
    }
}