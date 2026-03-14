package com.sudhanshu.aiquiz.feature_quiz.presentation.result.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.core.utils.Utils

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicsDisplay(
    topicsList: List<String>
) {
//    val sampleList = listOf("Big bang theory", "Stuart", "Star Wars The return of Jedi")

    val fontFamily = Utils.fontFamily

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        if (topicsList.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 16.dp, start = 18.dp),
                text = "Topics Selected",
                fontFamily = fontFamily,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onTertiary
            )
            FlowRow(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                topicsList.forEach { topic ->
                    TopicListComponent(
                        topic = topic,
                        fontFamily = fontFamily,
                    )
                }
            }
        }
    }
}

@Composable
fun TopicListComponent(
    topic: String,
    fontFamily: FontFamily,
) {
    Box(
        modifier = Modifier.padding(8.dp)
    ) {
        Card(
            modifier = Modifier,
//                .height(38.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = ShapeDefaults.Large
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    text = topic,
                    fontFamily = fontFamily,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}