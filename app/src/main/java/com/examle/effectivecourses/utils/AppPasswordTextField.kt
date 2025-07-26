package com.examle.effectivecourses.utils

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.R
import com.examle.effectivecourses.ui.theme.BottomItemIndicatorColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppPasswordTextField(hint : String, modifier: Modifier, onTextChange : (text : String) -> Unit) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = password,
        onValueChange = {
            password = it
            onTextChange.invoke(it)
        },
        modifier = modifier,
        interactionSource = interactionSource,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White
        ),
        cursorBrush = SolidColor(Color.White),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = password,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 0.dp),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = BottomItemIndicatorColor,
                unfocusedContainerColor = BottomItemIndicatorColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = hint,
                    color = CourseItemTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            trailingIcon = {
                val image = if (passwordVisible)
                    R.drawable.ic_password_toggle_hidden
                else R.drawable.ic_password_toggle_shown

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(painterResource(image), description)
                }
            }

        )
    }
}