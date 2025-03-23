package com.danielvilha.bmicalculator

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.danielvilha.bmicalculator.ui.screen.BMICalculator
import com.danielvilha.bmicalculator.ui.theme.BMICalculatorTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BMICalculatorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bmiCalculator_showsResult_whenInputsAreValid() {
        composeTestRule.setContent {
            BMICalculator(MainViewModel())
        }

        composeTestRule
            .onNodeWithText("Type your weight (kg) - e.g: 73.50")
            .performTextInput("70")

        composeTestRule
            .onNodeWithText("Type your height (cm) - e.g: 178")
            .performTextInput("175")

        composeTestRule
            .onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule
            .onNodeWithText("22.86")
            .assertExists()

        composeTestRule
            .onNodeWithText("BMI Result:")
            .assertExists()

        composeTestRule
            .onNodeWithTag("bmiResultText")
            .assertExists()
            .assertTextEquals("Normal")
    }

    @Test
    fun bmiCalculator_showsSnackbar_whenFieldsAreEmpty() {
        composeTestRule.setContent {
            BMICalculator(MainViewModel())
        }

        composeTestRule
            .onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule
            .onNodeWithText("Please enter both weight and height.")
            .assertExists()
    }

    @Test
    fun bmiCalculator_invalidInput_shouldNotCrash_andShowSnackbar() {
        composeTestRule.setContent {
            BMICalculator(MainViewModel())
        }

        composeTestRule
            .onNodeWithText("Type your weight (kg) - e.g: 73.50")
            .performTextInput("abc") // invalid input

        composeTestRule
            .onNodeWithText("Type your height (cm) - e.g: 178")
            .performTextInput("175")

        composeTestRule
            .onNodeWithText("CALCULATE")
            .performClick()

        composeTestRule
            .onNodeWithText("Please enter both weight and height.")
            .assertExists()
    }

    @Test
    fun clickingNewButton_shouldClearFields() {
        val viewModel = MainViewModel()
        composeTestRule.setContent {
            BMICalculator(viewModel)
        }

        composeTestRule.onNodeWithText("Type your weight (kg) - e.g: 73.50")
            .performTextInput("88")

        composeTestRule.onNodeWithText("Type your height (cm) - e.g: 178")
            .performTextInput("180")

        composeTestRule.onNodeWithContentDescription("New Button")
            .performClick()

        composeTestRule.onNodeWithText("Type your weight (kg) - e.g: 73.50")
            .assertTextEquals("")

        composeTestRule.onNodeWithText("Type your height (cm) - e.g: 178")
            .assertTextEquals("")
    }

    @Test
    fun bmiCalculator_appearsCorrectlyInDarkTheme() {
        composeTestRule.setContent {
            BMICalculatorTheme(darkTheme = true) {
                BMICalculator(MainViewModel())
            }
        }

        composeTestRule.onNodeWithText("BMI CALCULATOR").assertExists()
    }

    @Test
    fun bmiCalculator_accessibility_check() {
        composeTestRule.setContent {
            BMICalculator(MainViewModel())
        }

        composeTestRule
            .onNodeWithContentDescription("Weight Icon")
            .assertExists()

        composeTestRule
            .onNodeWithContentDescription("Height Icon")
            .assertExists()

        composeTestRule
            .onNodeWithText("CALCULATE")
            .assertHasClickAction()
    }
}