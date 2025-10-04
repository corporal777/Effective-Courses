package com.examle.effectivecourses.ui.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.extensions.showCustomTabsBrowser
import com.examle.effectivecourses.extensions.verticalGradientBrush
import com.examle.effectivecourses.ui.components.LoadingButton
import com.examle.effectivecourses.ui.login.LoginViewModel
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorBottom
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorTop
import com.examle.effectivecourses.ui.theme.VkColor
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun ActionsItem(viewModel: LoginViewModel) {

    val context = LocalContext.current
    val isEnabled by viewModel.buttonEnabled.collectAsState()
    val isLoading by viewModel.buttonLoading.collectAsState()

    LoadingButton(
        text = "Вход",
        backColor = CourseMoreTextColor,
        loading = isLoading,
        enabled = isEnabled,
        onClick = { viewModel.loginProfile() }
    )

    val text = buildAnnotatedString {
        append(AnnotatedString("Нету аккаунта?", spanStyle = SpanStyle(CourseItemTextColor)))
        append(AnnotatedString(" Регистрация ", spanStyle = SpanStyle(CourseMoreTextColor)))
        append(AnnotatedString("\nЗабыл пароль", spanStyle = SpanStyle(CourseMoreTextColor)))
    }

    Text(
        text = text,
        color = Color.White,
        fontSize = 14.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    )


    Spacer(modifier = Modifier.size(10.dp, 30.dp))

    HorizontalDivider(
        color = BottomBarLineColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
            .padding(bottom = 20.dp)
    )

    Spacer(modifier = Modifier.size(10.dp, 30.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .height(40.dp)
                .background(VkColor)
                .clickable(Color.White) { showCustomTabsBrowser(context, "https://vk.com/") }
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.ic_vk), "vk")
        }


        Box(
            modifier = Modifier
                .clip(CircleShape)
                .height(40.dp)
                .background(
                    verticalGradientBrush(listOf(OdnoklassnikiColorTop, OdnoklassnikiColorBottom))
                )
                .clickable(Color.White) {
                    showCustomTabsBrowser(context, "https://ok.ru/")
                }
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.ic_odnoklassniki), "odnoklassniki")
        }
    }
}