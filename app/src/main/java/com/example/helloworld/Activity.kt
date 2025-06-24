package com.example.helloworld

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "activities")
data class Activity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="user") val user: String?,
    @ColumnInfo(name="duration") val duration: String,
    @ColumnInfo(name="type") val type: String,
    @ColumnInfo(name="distance") val distance: String,
    @ColumnInfo(name="date") val date: String,
    @ColumnInfo(name="startTime") val startTime: String,
    @ColumnInfo(name="finishTime") val finishTime: String,
    @ColumnInfo(name="timeAgo") val timeAgo: String,
    @ColumnInfo(name="isFromOtherUser") val isFromOtherUser: Boolean?
) : Serializable