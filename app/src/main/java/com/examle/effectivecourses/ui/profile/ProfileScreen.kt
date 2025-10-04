package com.examle.effectivecourses.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.examle.domain.model.DataState
import com.examle.domain.model.MyCourseModel
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.components.ProgressDialog
import com.examle.effectivecourses.ui.components.ShimmerItem
import com.examle.effectivecourses.ui.components.TextMedium
import com.examle.effectivecourses.ui.components.TextNormal
import com.examle.effectivecourses.ui.components.TextSemibold
import com.examle.effectivecourses.ui.components.TextWithIcon
import com.examle.effectivecourses.ui.profile.components.MyCourseItem
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.BottomItemIndicatorColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseLessonColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    padding: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLogout : () -> Unit
) {

    val uiState by viewModel.courses.collectAsState()

    val isLogoutSuccess by viewModel.logoutSuccess.collectAsState()
    LaunchedEffect(isLogoutSuccess) {
        if (isLogoutSuccess) onLogout.invoke()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, padding.calculateTopPadding() + 30.dp, 20.dp, 120.dp)
    ) {

        HeaderItem()

        ProfileButtonItem("Написать в поддержку", 1){}
        ButtonDivider()
        ProfileButtonItem("Настройки", 2){}
        ButtonDivider()
        ProfileButtonItem("Выйти из аккаунта", 3){ viewModel.logoutProfile() }

        TitleItem()

        when(uiState){
            is DataState.Error -> { }
            is DataState.Loading -> ShimmerItem()
            is DataState.Success -> {
                val data = (uiState as DataState.Success<List<MyCourseModel>>).data
                data.forEach { MyCourseItem(it) }
            }
        }
    }
}


@Composable
private fun HeaderItem() {
    TextSemibold(
        modifier = Modifier.fillMaxWidth(),
        text = "Профиль",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 20.dp))
}

@Composable
private fun ProfileButtonItem(text: String, position: Int, onClick : () -> Unit) {
    val shape = if (position == 1) RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    else if (position == 3) RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    else RectangleShape

    Row(
        modifier = Modifier
            .clip(shape)
            .fillMaxWidth()
            .background(CourseItemColor)
            .clickable(Color.White){ onClick.invoke() }
            .padding(vertical = 12.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextMedium(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = text,
            color = CourseItemTextColor,
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = "",
            tint = CourseItemTextColor,
            modifier = Modifier.size(15.dp)
        )
    }
}

@Composable
fun TitleItem() {
    Spacer(modifier = Modifier.size(10.dp, 30.dp))

    TextMedium(
        modifier = Modifier.fillMaxWidth(),
        text = "Ваши курсы",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 15.dp))
}




@Composable
private fun ButtonDivider() {
    HorizontalDivider(
        color = BottomBarLineColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp)
            .background(CourseItemColor)
            .padding(horizontal = 15.dp)
    )
}