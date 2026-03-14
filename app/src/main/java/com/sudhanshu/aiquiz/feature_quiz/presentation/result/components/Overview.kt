package com.sudhanshu.aiquiz.feature_quiz.presentation.result.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.result.ResultState

@Composable
fun Overview(
    resultState: ResultState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        OverviewRowView(
            label = "Correct Answers",
            valueToShow = resultState.correctAnswers.toString()
        )
        OverviewRowView(
            label = "Incorrect Answers",
            valueToShow = (resultState.totalQuestions - resultState.correctAnswers).toString()
        )
        OverviewRowView(
            label = "Total Score",
            valueToShow = if(resultState.totalQuestions!=0) (resultState.correctAnswers*100/resultState.totalQuestions).toString()+"%" else "0%"
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun OverviewRowView(
    label: String,
    valueToShow: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(1f),
            text = label,
            fontFamily = Utils.fontFamily,
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = valueToShow,
                fontFamily = Utils.fontFamily,
                color = Color.Black,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}