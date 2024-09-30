package com.example.mfit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mfit.entity.StepsCounter
import com.example.mfit.repository.DatabaseRepo
import com.example.mfit.utils.Helper
import kotlinx.coroutines.launch


class MainViewModel(private val repository: DatabaseRepo) : ViewModel() {


    private val _steps = MutableLiveData<Int>().apply { value = 0 }
    val steps: LiveData<Int> = _steps

    val allWords: LiveData<List<StepsCounter>> = repository.allStepsData.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    private fun insertStepsData(stepsCounter: StepsCounter) = viewModelScope.launch {
        repository.updateSteps(stepsCounter)
    }

    fun updateSteps(input: Int) {
        _steps.apply { value =  input}
        insertStepsData(StepsCounter(Helper.getCurrentDate(),input))
    }

}

class MainViewModelFactory(private val repository: DatabaseRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}