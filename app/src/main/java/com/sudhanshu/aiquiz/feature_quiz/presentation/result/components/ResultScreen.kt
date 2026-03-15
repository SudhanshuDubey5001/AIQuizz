package com.sudhanshu.aiquiz.feature_quiz.presentation.result.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sudhanshu.aiquiz.core.presentation.components.QuizAppNavigationBar
import com.sudhanshu.aiquiz.core.utils.AppConfigurationConstants
import com.sudhanshu.aiquiz.core.utils.Screens
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.result.Grade
import com.sudhanshu.aiquiz.feature_quiz.presentation.result.ResultScreenEvents
import com.sudhanshu.aiquiz.feature_quiz.presentation.result.ResultScreenVM
import com.sudhanshu.aiquiz.feature_quiz.presentation.welcome.components.AppButton
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    onNavigate: (route: String) -> Unit,
    viewModel: ResultScreenVM = hiltViewModel()
) {

    val resultState = viewModel.resultState.value
    val loadingResource = viewModel.loadingResources.value

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    fun openURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    fun navigateBackToTopicScreen(){
        viewModel.resetQuiz()
        onNavigate(Screens.BACK)
    }

    val performanceUser = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        when (resultState.grade) {
            Grade.VERY_BAD -> performanceUser.value = AppConfigurationConstants.RESULT_MSG_VERY_BAD
            Grade.BAD -> performanceUser.value = AppConfigurationConstants.RESULT_MSG_BAD
            Grade.FINE -> performanceUser.value = AppConfigurationConstants.RESULT_MSG_FINE
            Grade.GOOD -> performanceUser.value = AppConfigurationConstants.RESULT_MSG_GOOD
            Grade.VERY_GOOD -> performanceUser.value =
                AppConfigurationConstants.RESULT_MSG_VERY_GOOD

            Grade.EXCELLENT -> performanceUser.value =
                AppConfigurationConstants.RESULT_MSG_EXCELLENT
        }

        if (resultState.scorePercentage == 1f) performanceUser.value =
            AppConfigurationConstants.RESULT_MSG_PERFECT_SCORE
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Modifier.padding(it)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            QuizAppNavigationBar(
                heading = "",
                onClickBackButton = {
                    viewModel.onEvents(ResultScreenEvents.ResetQuizData)
                    navigateBackToTopicScreen()
                })

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 30.dp),
                    text = performanceUser.value,
                    fontFamily = Utils.fontFamily,
                    fontSize = 32.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBarWithResult(
                        percentage = resultState.scorePercentage,
                        content = resultState.scoreText,
                        fontSize = 32.sp,
                        color = resultState.progressIndicatorColor
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                TopicsDisplay(
                    topicsList = viewModel.topics
                )

                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(10.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Performance",
                            fontSize = 24.sp,
                            fontFamily = Utils.fontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        QuizPieChart(resultState = resultState)

                        Spacer(modifier = Modifier.height(40.dp))

                        Overview(resultState = resultState)

                        Spacer(modifier = Modifier.height(30.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = "Resources",
                                fontSize = 24.sp,
                                fontFamily = Utils.fontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            if (loadingResource)
                                Box(
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(32.dp),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                        }

                        Spacer(modifier = Modifier.height(30.dp))

                        viewModel.resources.forEach { resource ->
                            ResourceView(
                                resource = resource,
                                openURL = { url ->
                                    openURL(url)
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AppButton(
                        text = "Play again ?",
                        fontFamily = Utils.fontFamily,
                        onClick = {
                            navigateBackToTopicScreen()
                        })
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}