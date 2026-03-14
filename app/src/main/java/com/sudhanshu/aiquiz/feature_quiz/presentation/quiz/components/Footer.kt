package com.sudhanshu.aiquiz.feature_quiz.presentation.quiz.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Footer(
    pagerState: PagerState,
    onClickSubmitButton: () -> Unit
) {
    val sizeOfArrows = 42.dp
    val scope = rememberCoroutineScope()

    fun goToNextPage() {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    fun goToPreviousPage() {
        scope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage - 1)
        }
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
//        Box(
//            modifier = Modifier
//                .align(Alignment.CenterVertically)
//                .weight(1f),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(sizeOfArrows)
//                    .clickable { goToPreviousPage() },
//                imageVector = Icons.Default.KeyboardArrowLeft,
//                contentDescription = "previous question"
//            )
//        }

//        Card(
//            modifier = Modifier
//                .weight(2f)
//                .clickable {
//                    onClickSubmitButton()
//                },
//            colors = CardDefaults.cardColors(
//                containerColor = MaterialTheme.colorScheme.tertiary
//            ),
//            elevation = CardDefaults.cardElevation(10.dp),
//        ) {
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp),
//                text = "Finish",
//                fontFamily = Utils.fontFamily,
//                fontSize = 14.sp,
//                textAlign = TextAlign.Center
//            )
//        }

//        Box(
//            modifier = Modifier
//                .align(Alignment.CenterVertically)
//                .weight(1f),
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(sizeOfArrows)
//                    .clickable { goToNextPage() },
//                imageVector = Icons.Default.KeyboardArrowRight,
//                contentDescription = "next question"
//            )
//        }
    }
}