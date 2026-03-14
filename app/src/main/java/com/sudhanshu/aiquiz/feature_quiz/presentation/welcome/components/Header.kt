package com.sudhanshu.aiquiz.feature_quiz.presentation.welcome.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.core.utils.Utils

@Composable
fun Header() {
    val fontFamily = Utils.fontFamily
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Party AI Quiz",
        fontFamily = fontFamily,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Unlimited Topics and Questions",
        fontFamily = fontFamily,
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
}