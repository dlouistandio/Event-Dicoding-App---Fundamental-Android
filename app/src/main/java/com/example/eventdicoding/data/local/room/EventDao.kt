package com.example.eventdicoding.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.eventdicoding.data.local.entity.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM event where bookmarked = 1")
    fun getBookmarkedEvent(): LiveData<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvent(event: EventEntity)

    @Update
    suspend fun updateEvent(news: EventEntity)

    @Query("DELETE FROM event WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM event WHERE name = :name AND bookmarked = 1)")
    suspend fun isEventBookmarked(name: String): Boolean

    @Query("SELECT * FROM event WHERE id = :eventId")
    fun getFavoriteEventById(eventId: Int): LiveData<EventEntity>
}