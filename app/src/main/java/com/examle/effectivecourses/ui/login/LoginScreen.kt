package com.examle.effectivecourses.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
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
import com.examle.effectivecourses.ui.home.HomeViewModel
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorBottom
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorTop
import com.examle.effectivecourses.ui.theme.VkColor
import com.examle.effectivecourses.utils.AppPasswordTextField
import com.examle.effectivecourses.utils.AppTextFieldSmall
import com.examle.effectivecourses.utils.LoadingButton
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    padding: PaddingValues,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(AppBackgroundColor)
            .padding(20.dp, padding.calculateTopPadding() + 20.dp, 20.dp, 120.dp)
    ) {
        ContentItem(viewModel)
        ActionsItem(viewModel)
    }

    viewModel.onLoginSuccess = { onLoginClick.invoke() }
}

@Composable
private fun ContentItem(viewModel: LoginViewModel) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Вход",
        color = CourseItemTextColor,
        fontSize = 27.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 30.dp))

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        text = "Email",
        color = CourseItemTextColor,
        fontSize = 17.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start
    )

    AppTextFieldSmall(
        hint = "example@gmail.com",
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) { viewModel.changeEmail(it) }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 5.dp),
        text = "Пароль",
        color = CourseItemTextColor,
        fontSize = 17.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start
    )

    AppPasswordTextField(
        hint = "Введите пароль",
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) { viewModel.changePassword(it) }

    Spacer(modifier = Modifier.size(10.dp, 30.dp))
}

@Composable
fun ActionsItem(viewModel: LoginViewModel) {
    val isEnabled by viewModel.buttonEnabled
    val isLoading by viewModel.loading

    LoadingButton(
        text = "Вход",
        backColor = CourseMoreTextColor,
        loading = isLoading,
        enabled = isEnabled
    ) {
        viewModel.loginProfile()
    }

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

    val context = LocalContext.current

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