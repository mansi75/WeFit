package com.example.mfit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StepsCounter(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "counter") val counter: Int?
)
