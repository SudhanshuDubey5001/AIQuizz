package com.sudhanshu.aiquiz.feature_quiz.presentation.result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.feature_quiz.presentation.result.ResultState

@Composable
fun QuizPieChart(
    resultState: ResultState
) {
    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(
                "SciFi",
                (resultState.scorePercentage * 100),
                AppConfigurationConstants.PIE_CHART_CORRECT_COLOR
            ),
            PieChartData.Slice(
                "Romance",
                (100 - (resultState.scorePercentage * 100)),
                AppConfigurationConstants.PIE_CHART_INCORRECT_COLOR
            )
        ),
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = false,
        animationDuration = 1500,
    )

    PieChart(
        modifier = Modifier,
        pieChartData,
        pieChartConfig
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Correct",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppConfigurationConstants.PIE_CHART_CORRECT_COLOR
                )
            ) {}
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Incorrect",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppConfigurationConstants.PIE_CHART_INCORRECT_COLOR
                )
            ) {}
        }
    }
}