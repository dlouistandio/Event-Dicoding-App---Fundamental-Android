package com.example.eventdicoding.data.remote.retrofit

import com.example.eventdicoding.data.remote.response.DetailRespones
import com.example.eventdicoding.data.remote.response.EventResponses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvent(
        @Query("active") id: Int
    ): EventResponses

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int
    ): DetailRespones
}