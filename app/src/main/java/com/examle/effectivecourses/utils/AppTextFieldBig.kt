package com.examle.effectivecourses.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.R
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor

@Composable
fun AppTextFieldBig(modifier: Modifier, onTextChange : (text : String) -> Unit) {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            onTextChange.invoke(text)
        },
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = "Search courses...",
                color = CourseItemTextColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = CourseItemColor,
            unfocusedContainerColor = CourseItemColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(25.dp),
        textStyle = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = "",
                tint = Color.White,
            )
        }
    )
}