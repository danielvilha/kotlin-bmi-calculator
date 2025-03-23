package com.danielvilha.bmicalculator

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `onWeightChange should update weight`() {
        viewModel.onWeightChange("70")
        assertEquals("70", viewModel.weight)
    }

    @Test
    fun `onHeightChange should update height`() {
        viewModel.onHeightChange("175")
        assertEquals("175", viewModel.height)
    }

    @Test
    fun `onValueChange should update value`() {
        viewModel.onValueChange("23.45")
        assertEquals("23.45", viewModel.value)
    }

    @Test
    fun `onResultChange should update result`() {
        viewModel.onResultChange("Normal")
        assertEquals("Normal", viewModel.result)
    }

    @Test
    fun `calculateBMI should show snackbar when inputs are blank`() {
        viewModel.calculateBMI()
        assertTrue(viewModel.showSnackBar)
    }

    @Test
    fun `calculateBMI should calculate correct BMI and result`() {
        viewModel.onWeightChange("70")
        viewModel.onHeightChange("175")

        viewModel.calculateBMI()

        assertEquals("22.86", viewModel.value)
        assertEquals("Normal", viewModel.result)
        assertFalse(viewModel.showSnackBar)
    }

    @Test
    fun `clean should reset all values`() {
        viewModel.onWeightChange("70")
        viewModel.onHeightChange("175")
        viewModel.onValueChange("22.86")
        viewModel.onResultChange("Normal")

        viewModel.clean()

        assertEquals("", viewModel.weight)
        assertEquals("", viewModel.height)
        assertEquals("", viewModel.value)
        assertEquals("", viewModel.result)
    }

    @Test
    fun `dismissSnackBar should set showSnackBar to false`() {
        viewModel.onWeightChange("")
        viewModel.onHeightChange("")
        viewModel.calculateBMI()
        assertTrue(viewModel.showSnackBar)

        viewModel.dismissSnackBar()
        assertFalse(viewModel.showSnackBar)
    }
}