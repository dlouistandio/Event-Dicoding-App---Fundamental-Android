package com.example.eventdicoding.data.di

import android.content.Context
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.room.EventDatabase
import com.example.eventdicoding.data.remote.retrofit.ApiConfig
import com.example.eventdicoding.ui.SettingPreferences
import com.example.eventdicoding.ui.dataStore

object Injection {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = EventDatabase.getInstance(context)
        val dao = database.eventDao()
        return EventRepository.getInstance(apiService, dao)
    }

    fun provideSettings(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }

}