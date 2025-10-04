package com.examle.effectivecourses.ui.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.ui.components.AppPasswordTextField
import com.examle.effectivecourses.ui.components.AppTextFieldSmall
import com.examle.effectivecourses.ui.components.TextMedium
import com.examle.effectivecourses.ui.components.TextSemibold
import com.examle.effectivecourses.ui.login.LoginViewModel
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun ContentItem(viewModel: LoginViewModel) {
    TextSemibold(
        modifier = Modifier.fillMaxWidth(),
        text = "Вход",
        color = CourseItemTextColor,
        fontSize = 27.sp,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 30.dp))

    TextMedium(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        text = "Email",
        color = CourseItemTextColor,
        fontSize = 17.sp,
        textAlign = TextAlign.Start
    )

    AppTextFieldSmall(
        hint = "example@gmail.com",
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
    ) { viewModel.changeEmail(it) }

    TextMedium(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 5.dp),
        text = "Пароль",
        color = CourseItemTextColor,
        fontSize = 17.sp,
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