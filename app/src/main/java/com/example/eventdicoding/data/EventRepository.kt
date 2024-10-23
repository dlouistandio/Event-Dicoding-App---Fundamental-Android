package com.example.eventdicoding.data

import androidx.lifecycle.LiveData
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.local.room.EventDao
import com.example.eventdicoding.data.local.room.EventDatabase
import com.example.eventdicoding.data.remote.response.ListEventsItem
import com.example.eventdicoding.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val eventDao: EventDao
) {
    suspend fun getEventData(id: Int): Result<List<ListEventsItem>> {
        return try {
            val response = apiService.getEvent(id)
            val eventList = response.listEvents
            Result.Success(eventList)
        } catch (e: Exception) {
            Result.Error("Exception: ${e.message}")
        }
    }

    suspend fun getEventDetail(id: Int): Result<EventEntity> {
        return try {
            val response = apiService.getDetailEvent(id)
            val event = response.event

            val isBookmarked = eventDao.isEventBookmarked(event.name)
            val eventEntity = EventEntity(
                id = id,
                name = event.name,
                beginTime = event.beginTime,
                mediaCover = event.imageLogo,
                endTime = event.endTime,
                ownerName = event.ownerName,
                description = event.description,
                link = event.link,
                quota = event.quota,
                registrants = event.registrants,
                isBookmarked = isBookmarked
            )
            eventDao.insertEvent(eventEntity)
            Result.Success(eventEntity)
        } catch (e: Exception) {
            Result.Error("Exception: ${e.message}")
        }
    }

    fun getBookmarkedEvent(): LiveData<List<EventEntity>> {
        return eventDao.getBookmarkedEvent()
    }

    suspend fun setEventBookmark(event: EventEntity, bookmarkState: Boolean) {
        event.isBookmarked = bookmarkState
        eventDao.updateEvent(event)
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: EventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}