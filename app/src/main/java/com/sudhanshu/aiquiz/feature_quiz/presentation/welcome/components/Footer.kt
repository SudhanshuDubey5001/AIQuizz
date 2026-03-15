package com.sudhanshu.aiquiz.feature_quiz.presentation.welcome.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sudhanshu.aiquiz.core.utils.Utils

@Composable
fun Footer(navigateToTopicScreen: () -> Unit) {
    val fontFamily = Utils.fontFamily
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppButton(text = "Play", fontFamily = fontFamily, onClick = {
            navigateToTopicScreen()
        })

        Spacer(modifier = Modifier.height(8.dp))

//        AppButton(text = "Options", fontFamily = fontFamily, onClick = {
//            // TODO: implement Option screen
//        })
    }
}