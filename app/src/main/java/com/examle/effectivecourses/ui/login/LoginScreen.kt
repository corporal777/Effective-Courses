package com.examle.effectivecourses.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorBottom
import com.examle.effectivecourses.ui.theme.OdnoklassnikiColorTop
import com.examle.effectivecourses.ui.theme.VkColor
import com.examle.effectivecourses.ui.components.AppPasswordTextField
import com.examle.effectivecourses.ui.components.AppTextFieldSmall
import com.examle.effectivecourses.ui.components.LoadingButton
import com.examle.effectivecourses.ui.components.TextSemibold
import com.examle.effectivecourses.ui.login.components.ActionsItem
import com.examle.effectivecourses.ui.login.components.ContentItem
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    padding: PaddingValues,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginClick: () -> Unit
) {

    val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()
    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess) onLoginClick.invoke()
    }

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
}



