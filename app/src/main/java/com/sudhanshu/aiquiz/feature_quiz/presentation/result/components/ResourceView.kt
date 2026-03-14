package com.sudhanshu.aiquiz.feature_quiz.presentation.result.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.Resource

@Composable
fun ResourceView(
    resource: Resource,
    openURL: (url: String) -> Unit
) {
    Text(
        modifier = Modifier.clickable {
            openURL(resource.url)
        },
        text = resource.url,
        fontSize = 18.sp,
        color = Color.Blue,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = resource.explanation,
        fontSize = 18.sp,
        fontFamily = Utils.fontFamily,
        color = Color.Black,
    )
    Spacer(modifier = Modifier.height(20.dp))
}