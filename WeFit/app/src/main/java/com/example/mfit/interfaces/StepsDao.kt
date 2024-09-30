package com.example.mfit.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.mfit.entity.StepsCounter
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsDao {
    @Query("SELECT * FROM StepsCounter")
    fun getAll(): List<StepsCounter>

    @Insert
    suspend fun insertSteps(stepsC: StepsCounter)

    @Upsert
    suspend fun update(note: StepsCounter)

    @Delete
    fun delete(note: StepsCounter)

    @Query("SELECT * FROM StepsCounter")
    fun getStepsData(): Flow<List<StepsCounter>>
}