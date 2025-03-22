package com.danielvilha.bmicalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.Locale

class MainViewModel : ViewModel() {

    var weight by mutableStateOf("")
        private set

    var height by mutableStateOf("")
        private set

    var value by mutableStateOf("")
        private set

    var result by mutableStateOf("")
        private set

    var showSnackBar by mutableStateOf(false)
        private set

    fun onWeightChange(newWeight: String) {
        weight = newWeight
    }

    fun onHeightChange(newHeight: String) {
        height = newHeight
    }

    fun onValueChange(newValue: String) {
        value = newValue
    }

    fun onResultChange(newResult: String) {
        result = newResult
    }

    fun calculateBMI() {
        if (weight.isBlank() || height.isBlank()) {
            showSnackBar = true
        } else {
            val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
            onValueChange(String.Companion.format(Locale.US, "%.2f", bmi))
            onResultChange(
                when {
                    bmi <= 18.5f -> "Underweight"
                    bmi <= 24.99f -> "Normal"
                    bmi <= 29.99f -> "Overweight"
                    bmi <= 34.99f -> "Obesity Class I"
                    bmi <= 39.99f -> "Obesity Class II"
                    else -> "Obesity Class III"
                }
            )
            showSnackBar = false
        }
    }

    fun clean() {
        onWeightChange("")
        onHeightChange("")
        onValueChange("")
        onResultChange("")
    }

    fun dismissSnackBar() {
        showSnackBar = false
    }
}