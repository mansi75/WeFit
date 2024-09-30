package com.example.mfit.repository

import androidx.annotation.WorkerThread
import com.example.mfit.entity.StepsCounter
import com.example.mfit.interfaces.StepsDao
import kotlinx.coroutines.flow.Flow

class DatabaseRepo (private val stepsDao: StepsDao){
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allStepsData: Flow<List<StepsCounter>> = stepsDao.getStepsData()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun insertSteps(stepsCounter: StepsCounter) {
        stepsDao.insertSteps(stepsCounter)
    }

    @WorkerThread
    suspend fun updateSteps(stepsCounter: StepsCounter) {
        stepsDao.update(stepsCounter)
    }
}