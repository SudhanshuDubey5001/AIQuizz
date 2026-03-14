package com.sudhanshu.aiquiz.feature_quiz.presentation.topics.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.R
import com.sudhanshu.aiquiz.core.utils.ErrorMessages
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.presentation.topics.TopicState
import com.sudhanshu.aiquiz.feature_quiz.presentation.topics.Topic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicInput(
    inputProp: Topic,
    onValueChanged: (newValue: String) -> Unit,
    onSubmitTopic: () -> Unit
) {
    val fontFamily = Utils.fontFamily
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Which topic?",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            fontFamily = fontFamily
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Type any topic you like")
            },
            value = inputProp.name,
            onValueChange = { newValue -> onValueChanged(newValue) },
            textStyle = TextStyle(
                fontSize = 18.sp,
                fontFamily = fontFamily,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                onSubmitTopic()
            }),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
            },
            trailingIcon = {
                if (inputProp.name.isNotEmpty()) {
                    if (inputProp.topicState == TopicState.LOADING) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    } else if (inputProp.topicState == TopicState.VALID) {
                        Image(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent),
                            painter = painterResource(R.drawable.validatedicon),
                            contentDescription = "green tick icon | validated"
                        )
                    } else if (inputProp.topicState == TopicState.INVALID) {
                        Image(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Transparent),
                            painter = painterResource(R.drawable.invalidateicon),
                            contentDescription = "red cross icon | not validated"
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (inputProp.name.isNotEmpty()) {
            if (inputProp.topicState == TopicState.INVALID) {
                ErrorMessageDisplay(
                    errorMessage = ErrorMessages.INVALID_TOPIC
                )
            } else if (inputProp.topicState == TopicState.EXIST_IN_LIST) {
                ErrorMessageDisplay(
                    errorMessage = ErrorMessages.ALREADY_PRESENT
                )
            } else if (
                inputProp.topicState == TopicState.LOADING &&
                inputProp.isSubmitted
            ) {
                ErrorMessageDisplay(
                    errorMessage = ErrorMessages.STILL_LOADING
                )
            }
        }
    }
}