package com.sudhanshu.aiquiz.feature_quiz.presentation.options.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudhanshu.aiquiz.core.utils.Utils
import com.sudhanshu.aiquiz.feature_quiz.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInput(
    user: User,
    onValueChanged: (value: String) -> Unit,
) {
    val focus = LocalFocusManager.current
    val fontSize = 18.sp

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
        placeholder = {
            Text(
                text = "What's your name buddy?",
                fontSize = fontSize,
                fontFamily = Utils.fontFamily
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        ),
        value = user.name,
        onValueChange = { newValue -> onValueChanged(newValue) },
        textStyle = TextStyle(
            fontSize = fontSize,
            fontFamily = Utils.fontFamily,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focus.clearFocus()
        }),
    )
}