package com.examle.effectivecourses.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.examle.domain.model.MyCourseModel
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.components.TextMedium
import com.examle.effectivecourses.ui.components.TextNormal
import com.examle.effectivecourses.ui.components.TextWithIcon
import com.examle.effectivecourses.ui.theme.BottomItemIndicatorColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseLessonColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun MyCourseItem(course: MyCourseModel) {
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


        TextNormal(
            text = course.startDate,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier
                .clip(CircleShape)
                .background(CourseFavoriteBackColor)
                .constrainAs(date) {
                    start.linkTo(favBlur.end, 10.dp)
                    top.linkTo(favBlur.top)
                }
                .padding(horizontal = 10.dp)
        )

        TextMedium(
            text = course.title,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 15.dp, end = 15.dp)
                .constrainAs(title) { top.linkTo(image.bottom) }
        )

        TextNormal(
            text = course.percent,
            color = CourseMoreTextColor,
            fontSize = 14.sp,
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
            append(
                AnnotatedString("/" + course.lessons.second.toString() + " уроков", SpanStyle(
                    CourseLessonColor
                )
                )
            )
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