package com.sudhanshu.aiquiz.feature_quiz.presentation.loading.components


import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sudhanshu.aiquiz.core.presentation.UiEvent
import com.sudhanshu.aiquiz.core.presentation.components.SetStatusBarColor
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.loading.LoadingScreenEvents
import com.sudhanshu.aiquiz.feature_quiz.presentation.loading.LoadingScreenVM
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.graphicsLayer
import com.sudhanshu.aiquiz.core.utils.Screens

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreen(
    onNavigate: (route: String) -> Unit,
    viewModel: LoadingScreenVM = hiltViewModel()
) {

    val error = viewModel.error.value
    val retrySafetyCount = viewModel.retryCountSafety.value

    fun backToTopicScreen(){
        onNavigate(Screens.BACK)
    }

    LaunchedEffect(Unit){
        viewModel.uiEvent.collectLatest { event ->
            when(event){
                is UiEvent.navigate -> {
                    onNavigate(event.screen)
                }
                is UiEvent.showSnackBar -> Unit
            }
        }
    }

    SetStatusBarColor(color = MaterialTheme.colorScheme.primary)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Modifier.padding(it)
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 40.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "AI is creating your quiz",
                    fontSize = 42.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 50.sp,
                    fontFamily = Utils.fontFamily
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .padding(vertical = 40.dp, horizontal = 0.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!error) {
                    val infiniteTransition = rememberInfiniteTransition(label = "rotation")

                    val angle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(2000)
                        ),
                        label = "angle"
                    )

                    Icon(
                        imageVector = Icons.Default.Api,
                        contentDescription = "Loading",
                        modifier = Modifier
                            .size(280.dp)
                            .graphicsLayer {
                                rotationZ = angle
                            }
                    )
                }
                else {
                    ErrorView(
                        retrySafetyCount = retrySafetyCount,
                        onRetryClick = {
                            backToTopicScreen()
//                            viewModel.onEvents(LoadingScreenEvents.retryGeneratingQuiz)
                        }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyScreenPreview() {
//    LoadingScreen()
//}