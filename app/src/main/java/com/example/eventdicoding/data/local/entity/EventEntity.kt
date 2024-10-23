package com.example.eventdicoding.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
class EventEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    var id: Int,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "beginTime")
    val beginTime: String,

    @field:ColumnInfo(name = "mediaCover")
    val mediaCover: String? = null,

    @field:ColumnInfo(name = "endTime")
    val endTime: String? = null,

    @field:ColumnInfo(name = "ownerName")
    val ownerName: String? = null,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "link")
    val link: String,

    @field:ColumnInfo(name = "quota")
    val quota: Int,

    @field:ColumnInfo(name = "registrants")
    val registrants: Int,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
)