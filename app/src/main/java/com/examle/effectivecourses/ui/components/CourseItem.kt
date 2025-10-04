package com.examle.effectivecourses.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.examle.domain.model.CourseModel
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor

@Composable
fun CourseItem(
    course: CourseModel,
    onFavoriteClick: (CourseModel) -> Unit,
    onItemClick: (String) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CourseItemColor)
            .clickable(Color.White) { onItemClick.invoke(course.id) }
            .padding(bottom = 15.dp)
    ) {


        val (image, title, text, price, more, moreIcon, favorite, favBlur, date) = createRefs()

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
            painter = painterResource(if (course.isLiked) R.drawable.ic_favorite_fill else R.drawable.ic_favorite),
            contentDescription = "",
            tint = if (course.isLiked) CourseMoreTextColor else CourseFavoriteIconColor,
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp)
                .background(CourseFavoriteBackColor)
                .constrainAs(favorite) {
                    top.linkTo(image.top, 10.dp)
                    end.linkTo(image.end, 10.dp)
                }
                .clickable(Color.White) { onFavoriteClick.invoke(course) }
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
            fontSize = 14.sp,
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
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 15.dp, end = 15.dp)
                .constrainAs(title) { top.linkTo(image.bottom) }
        )

        TextNormal(
            text = course.text,
            fontSize = 15.sp,
            maxLines = 2,
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 15.dp, end = 15.dp)
                .alpha(0.7f)
                .constrainAs(text) { top.linkTo(title.bottom) }
        )

        TextMedium(
            text = course.price,
            fontSize = 16.sp,
            maxLines = 2,
            lineHeight = 20.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = 15.dp)
                .constrainAs(price) {
                    start.linkTo(parent.start)
                    top.linkTo(text.bottom)
                }
        )

        TextSemibold(
            text = "Подробнее",
            color = CourseMoreTextColor,
            fontSize = 15.sp,
            modifier = Modifier
                .constrainAs(more) {
                    end.linkTo(moreIcon.start, 5.dp)
                    baseline.linkTo(price.baseline)
                }
                .clickable(Color.White) { onItemClick.invoke(course.id) }
        )
        Icon(
            painter = painterResource(R.drawable.ic_more_arrow),
            contentDescription = "",
            tint = CourseMoreTextColor,
            modifier = Modifier.constrainAs(moreIcon) {
                end.linkTo(parent.end, 15.dp)
                top.linkTo(more.top)
                bottom.linkTo(more.bottom)
            })
    }

    Spacer(modifier = Modifier.size(10.dp, 20.dp))
}