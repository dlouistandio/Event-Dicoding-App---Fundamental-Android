package com.example.eventdicoding.ui.detailsEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventdicoding.data.EventRepository
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.local.entity.EventEntity
import kotlinx.coroutines.launch

class DetailsEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventDetail = MutableLiveData <Result<EventEntity>>()
    val eventDetail: LiveData<Result<EventEntity>> get() = _eventDetail

    fun findEventDetail(id: Int) {
        _eventDetail.value = Result.Loading
        viewModelScope.launch {
            val result = eventRepository.getEventDetail(id)
            _eventDetail.value = result
        }
    }

    fun saveEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.setEventBookmark(event, true)
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            eventRepository.setEventBookmark(event, false)
        }
    }
}
