package com.example.eventdicoding.ui.favoriteEvent

import androidx.lifecycle.ViewModel
import com.example.eventdicoding.data.EventRepository

class FavoriteEventViewModel(eventRepository: EventRepository) : ViewModel() {
    val favoriteEvent = eventRepository.getBookmarkedEvent()
}