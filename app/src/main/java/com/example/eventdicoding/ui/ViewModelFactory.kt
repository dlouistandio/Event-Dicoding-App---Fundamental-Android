package com.example.eventdicoding.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.di.Injection
import com.example.eventdicoding.ui.detailsEvent.DetailsEventViewModel
import com.example.eventdicoding.ui.favoriteEvent.FavoriteEventViewModel
import com.example.eventdicoding.ui.pastEvent.PastEventViewModel
import com.example.eventdicoding.ui.settings.SettingsViewModel
import com.example.eventdicoding.ui.upcomingEvent.UpcomingEventViewModel

class ViewModelFactory private constructor(private val eventRepository: EventRepository, private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            PastEventViewModel::class.java -> PastEventViewModel(eventRepository) as T
            UpcomingEventViewModel::class.java -> UpcomingEventViewModel(eventRepository) as T
            DetailsEventViewModel::class.java -> DetailsEventViewModel(eventRepository) as T
            FavoriteEventViewModel::class.java -> FavoriteEventViewModel(eventRepository) as T
            SettingsViewModel::class.java -> SettingsViewModel(pref) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }


    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideSettings(context)
                )
            }.also { instance = it }
    }
}