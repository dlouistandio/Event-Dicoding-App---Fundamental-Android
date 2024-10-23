package com.example.eventdicoding.ui.upcomingEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.local.entity.EventEntity
import kotlinx.coroutines.launch
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.remote.response.ListEventsItem

class UpcomingEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _listEvent = MutableLiveData<Result<List<ListEventsItem>>>()
    val listEvent: LiveData<Result<List<ListEventsItem>>> = _listEvent

    init {
        fetchUpcomingEvents()
    }

    fun fetchUpcomingEvents() {
        _listEvent.value = Result.Loading
        viewModelScope.launch {
            val fetch = eventRepository.getEventData(1)
            _listEvent.value = fetch
        }
    }

}