package com.example.getupdated.ui.article.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PeriodSelectionRow(
    selectedPeriod: Int,
    onPeriodSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val periods = listOf(1, 7, 30)
        periods.forEach { period ->
            // You can style the Button differently if 'period' == 'selectedPeriod'
            Button(
                onClick = { onPeriodSelected(period) },
                colors = if (period == selectedPeriod) {
                    // Example: tinted button for the selected period
                    ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                } else {
                    ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                }
            ) {
                Text(text = "$period days")
            }
        }
    }
}
