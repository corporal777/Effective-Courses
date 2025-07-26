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
import com.examle.effectivecourses.R
import com.examle.effectivecourses.dataSource.model.MyCourseModel
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.home.TextWithIcon
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.BottomItemIndicatorColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseLessonColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.effectivecourses.utils.ProgressDialog
import com.examle.effectivecourses.utils.ShimmerItem
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    padding: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLogout : () -> Unit
) {

    val uiState = viewModel.courses.value
    var loadingState by remember { mutableStateOf(false) }
    loadingState = viewModel.loading.value

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



        uiState.forEach { course ->
            if (course == null) ShimmerItem()
            else CourseItem(course)
        }

        viewModel.onLogoutSuccess = { onLogout.invoke() }
        if (loadingState) ProgressDialog()
    }
}


@Composable
private fun HeaderItem() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Профиль",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.SemiBold,
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
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = text,
            color = CourseItemTextColor,
            fontSize = 15.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Medium,
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

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Ваши курсы",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 15.dp))
}

@Composable
private fun CourseItem(course: MyCourseModel) {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CourseItemColor)
            .clickable(Color.White) { }
            .padding(bottom = 15.dp)
    ) {
        val (image, title, percent, percentLine, lesson, lessonLine, favorite, favBlur, date) = createRefs()

        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .height(130.dp)
                .constrainAs(image) { top.linkTo(parent.top) },
            painter = painterResource(R.drawable.pic1),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )

        Icon(
            painter = painterResource(if (course.hasLike) R.drawable.ic_favorite_fill else R.drawable.ic_favorite),
            contentDescription = "",
            tint = if (course.hasLike) CourseMoreTextColor else CourseFavoriteIconColor,
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp)
                .background(CourseFavoriteBackColor)
                .constrainAs(favorite) {
                    top.linkTo(image.top, 10.dp)
                    end.linkTo(image.end, 10.dp)
                }
                .padding(8.dp)
        )


        TextWithIcon(course.rate, modifier = Modifier
            .clip(CircleShape)
            .background(CourseFavoriteBackColor)
            .constrainAs(favBlur) {
                start.linkTo(image.start, 10.dp)
                bottom.linkTo(image.bottom, 10.dp)
            }
            .padding(start = 7.dp, end = 10.dp))


        Text(
            text = course.startDate.formatToDefaultDayMonthYearDate() ?: course.startDate,
            color = Color.White,
            fontSize = 15.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .clip(CircleShape)
                .background(CourseFavoriteBackColor)
                .constrainAs(date) {
                    start.linkTo(favBlur.end, 10.dp)
                    top.linkTo(favBlur.top)
                }
                .padding(horizontal = 10.dp)
        )

        Text(
            text = course.title,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 15.dp, end = 15.dp)
                .constrainAs(title) { top.linkTo(image.bottom) }
        )

        Text(
            text = course.percent.toString() + "%",
            color = CourseMoreTextColor,
            fontSize = 14.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(top = 10.dp, start = 15.dp)
                .constrainAs(percent) { top.linkTo(title.bottom) }
        )

        HorizontalDivider(
            color = CourseMoreTextColor,
            thickness = 5.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(percentLine) {
                    top.linkTo(percent.bottom)
                    start.linkTo(parent.start, 15.dp)
                    end.linkTo(lessonLine.start, 2.dp)
                    width = Dimension.fillToConstraints
                }
        )

        HorizontalDivider(
            color = BottomItemIndicatorColor,
            thickness = 5.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .constrainAs(lessonLine) {
                    top.linkTo(percentLine.top)
                    bottom.linkTo(percentLine.bottom)
                    start.linkTo(percentLine.end, 2.dp)
                    end.linkTo(parent.end, 15.dp)
                    width = Dimension.fillToConstraints
                }
        )

        val text = buildAnnotatedString {
            append(AnnotatedString(course.lessons.first.toString(), SpanStyle(CourseMoreTextColor)))
            append(AnnotatedString("/" + course.lessons.second.toString() + " уроков", SpanStyle(CourseLessonColor)))
        }

        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .constrainAs(lesson) {
                    end.linkTo(parent.end, 15.dp)
                    bottom.linkTo(lessonLine.top)
                }
        )
    }

    Spacer(modifier = Modifier.size(10.dp, 20.dp))
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