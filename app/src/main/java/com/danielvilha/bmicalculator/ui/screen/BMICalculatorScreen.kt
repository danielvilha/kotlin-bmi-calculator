package com.danielvilha.bmicalculator.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danielvilha.bmicalculator.MainViewModel
import com.danielvilha.bmicalculator.R
import com.danielvilha.bmicalculator.ui.theme.BMICalculatorTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun BMICalculatorLightPreview() {
    BMICalculatorTheme(darkTheme = false) {
        BMICalculator(
            MainViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BMICalculatorDarkPreview() {
    BMICalculatorTheme(darkTheme = true) {
        BMICalculator(
            MainViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BMICalculatorCalcLightPreview() {
    BMICalculatorTheme(darkTheme = false) {
        BMICalculator(
            MainViewModel().apply {
                onWeightChange("90")
                onHeightChange("180")
                onValueChange("27.78")
                onResultChange("Overweight")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BMICalculatorCalcDarkPreview() {
    BMICalculatorTheme(darkTheme = true) {
        BMICalculator(
            MainViewModel().apply {
                onWeightChange("90")
                onHeightChange("180")
                onValueChange("27.78")
                onResultChange("Overweight")
            }
        )
    }
}

@Composable
fun BMICalculator(viewModel: MainViewModel) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                IconButton(
                    onClick = { viewModel.clean() },
                    modifier = Modifier
                        .padding(top = 28.dp, start = 4.dp)
                        .align(Alignment.Start)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_new),
                        contentDescription = "New Button",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 62.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "BMI CALCULATOR",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fill in the fields with weight and height to calculate your body mass index.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )

                if (viewModel.result.isNotEmpty()) {
                    BMIValue(value = viewModel.value, result = viewModel.result)
                }
            }

            if (viewModel.value.isNotBlank()) {
                BMITableResult()
            }

            if (viewModel.result.isEmpty()) {
                BMIOutlinedText(viewModel = viewModel)
            }
        }

        Button(
            onClick = { viewModel.calculateBMI() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .imePadding(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("CALCULATE")
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (viewModel.showSnackBar) {
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar("Please enter both weight and height.")
                    viewModel.dismissSnackBar()
                }
            }
        }
    }
}

@Composable
private fun BMIValue(value: String, result: String) {
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.displayLarge
                .copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.surface
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "BMI Result:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.surface
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = result.toString(),
            modifier = Modifier.testTag("bmiResultText"),
            style = MaterialTheme.typography.titleLarge
                .copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.surface
        )

    }
}

@Composable
private fun BMITableResult() {
    Spacer(modifier = Modifier.height(48.dp))

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "BMI table for adults:",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Classification",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "BMI range (KG/m²)",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        val bmiTable = listOf(
            "Underweight" to "< 18.5",
            "Normal" to "18.5 - 24.9",
            "Overweight" to "25.0 - 29.9",
            "Obese Class I" to "30.0 - 34.9",
            "Obese Class II" to "35.0 - 39.9",
            "Obese Class III" to "> 40"
        )

        bmiTable.forEach { (classification, range) ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = classification, modifier = Modifier.weight(1f))
                Text(text = range, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BMIOutlinedText(viewModel: MainViewModel) {
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = viewModel.weight,
        onValueChange = viewModel::onWeightChange,
        label = { Text(text = "Type your weight (kg) - e.g: 73.50") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier
            .testTag("weightField")
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_weight),
                contentDescription = "Weight Icon"
            )
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = viewModel.height,
        onValueChange = viewModel::onHeightChange,
        label = { Text("Type your height (cm) - e.g: 178") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .testTag("heightField")
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = "Height Icon"
            )
        }
    )
}