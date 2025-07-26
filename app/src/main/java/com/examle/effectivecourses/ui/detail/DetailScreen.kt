package com.examle.effectivecourses.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.examle.effectivecourses.R
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.home.TextWithIcon
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseLessonColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.effectivecourses.utils.LoadingButton
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs

@Composable
fun DetailScreen(
    padding: PaddingValues,
    courseId: String,
    viewModel: DetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {

    val uiState = viewModel.courseDetail.value
    val scrollState = rememberScrollState()

    Box(Modifier.fillMaxSize().background(AppBackgroundColor)) {

        if (uiState == null) {
            LoadingItem()
            viewModel.getCourseDetail(courseId)
        }
        else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                HeaderItem(uiState)
                ContentItem(uiState)
            }
            TopBarItem(
                padding.calculateTopPadding(),
                uiState,
                scrollState,
                { onBackClick.invoke() },
                { viewModel.addCourseFavorite(uiState) }
            )
        }
    }
}

@Composable
private fun TopBarItem(
    topPadding: Dp,
    course: CourseModel,
    scrollState: ScrollState,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val scrollOffset = scrollState.value
    val alpha = abs(scrollOffset / (350).toFloat())

    val iconTint = if (alpha <= 0.7) AppBackgroundColor else CourseFavoriteIconColor
    val backTint = CourseFavoriteIconColor.copy(1.0f - alpha)

    var icon by remember { mutableStateOf(R.drawable.ic_favorite) }
    icon = if (course.hasLike) R.drawable.ic_favorite_fill else R.drawable.ic_favorite

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (back, fav, box, divider) = createRefs()

        Box(
            Modifier
                .background(CourseItemColor.copy(alpha))
                .constrainAs(box) {
                    bottom.linkTo(divider.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        HorizontalDivider(
            color = BottomBarLineColor.copy(alpha),
            thickness = 1.5.dp,
            modifier = Modifier
                .constrainAs(divider) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )



        Icon(
            painter = painterResource(R.drawable.ic_back),
            contentDescription = "",
            tint = iconTint,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(backTint)
                .clickable(Color.White) { onBackClick.invoke() }
                .padding(8.dp)
                .constrainAs(back) {
                    top.linkTo(parent.top, topPadding + 20.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    start.linkTo(parent.start, 18.dp)
                }
                .let {
                    val x = if (alpha <= 0.0f) 0.dp else {
                        val movedX = alpha * 5
                        if (movedX <= 9) movedX.dp else 9.dp
                    }
                    it.offset(x = -(x))
                }
        )


        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            tint = iconTint,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(backTint)
                .clickable(Color.White) { onAddClick.invoke() }
                .padding(10.dp)
                .constrainAs(fav) {
                    top.linkTo(parent.top, topPadding + 20.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end, 18.dp)
                }
                .let {
                    val x = if (alpha <= 0.0f) 0.dp else {
                        val movedX = alpha * 5
                        if (movedX <= 11) movedX.dp else 11.dp
                    }
                    it.offset(x = (x))
                }
        )
    }
}

@Composable
private fun HeaderItem(course: CourseModel) {
    ConstraintLayout {
        val (image, title, text) = createRefs()
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .constrainAs(image){ top.linkTo(parent.top) },
            painter = painterResource(R.drawable.image),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
        )

        TextWithIcon(course.rate, modifier = Modifier
            .clip(CircleShape)
            .background(CourseFavoriteBackColor)
            .constrainAs(title) {
                start.linkTo(image.start, 10.dp)
                bottom.linkTo(image.bottom, 10.dp)
            }
            .padding(start = 7.dp, end = 10.dp))

        Text(
            text = course.startDate.formatToDefaultDayMonthYearDate() ?: course.startDate,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .clip(CircleShape)
                .background(CourseFavoriteBackColor)
                .constrainAs(text) {
                    start.linkTo(title.end, 10.dp)
                    top.linkTo(title.top)
                }
                .padding(horizontal = 10.dp)
        )
    }


    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 25.dp),
        text = course.title,
        color = CourseItemTextColor,
        fontSize = 23.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.image_author),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
                .background(CourseFavoriteIconColor)
        )

        val text = buildAnnotatedString {
            append(
                AnnotatedString(
                    "Автор\n",
                    SpanStyle(
                        CourseLessonColor,
                        14.sp,
                        FontWeight.Normal,
                        fontFamily = TextUtils.robotoFont
                    )
                )
            )
            append(AnnotatedString("Merion Academy", SpanStyle(CourseItemTextColor)))
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp),
            text = text,
            color = CourseItemTextColor,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun ContentItem(course: CourseModel) {
    Spacer(modifier = Modifier.size(10.dp, 20.dp))
    LoadingButton(
        text = "Начать курс",
        backColor = CourseMoreTextColor,
        modifier = Modifier.padding(horizontal = 15.dp)
    ) { }

    Spacer(modifier = Modifier.size(10.dp, 10.dp))
    LoadingButton(
        text = "Перейти на платформу",
        backColor = CourseItemColor,
        modifier = Modifier.padding(horizontal = 15.dp)
    ) { }


    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 30.dp),
        text = "О курсе",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .alpha(0.7f),
        text = course.text,
        color = CourseItemTextColor,
        fontSize = 15.sp,
        minLines = 20,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = CourseItemTextColor,
            trackColor = Color.Transparent,
            strokeWidth = (5).dp,
            strokeCap = StrokeCap.Round
        )
    }

}

