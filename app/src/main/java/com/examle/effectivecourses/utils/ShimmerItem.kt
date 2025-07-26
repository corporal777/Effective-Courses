package com.examle.effectivecourses.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.examle.effectivecourses.extensions.shimmerLoading
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerItem() {
    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CourseItemColor)
            .shimmerLoading(2000)
            .padding(bottom = 15.dp)
    ) {
        val (image, title, favBlur, date) = createRefs()

        Box(
            Modifier
                .clip(RoundedCornerShape(15.dp))
                .border(2.dp, BottomBarLineColor, RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .height(130.dp)

                .constrainAs(image) { top.linkTo(parent.top) }
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(BottomBarLineColor)
                .constrainAs(favBlur) {
                    start.linkTo(image.start, 10.dp)
                    bottom.linkTo(image.bottom, 10.dp)
                }
                .width(100.dp)
                .height(25.dp)
                .padding(horizontal = 10.dp)
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(BottomBarLineColor)
                .constrainAs(date) {
                    start.linkTo(favBlur.end, 10.dp)
                    top.linkTo(favBlur.top)
                }
                .width(200.dp)
                .height(25.dp)
                .padding(horizontal = 10.dp)
        )

        Column(
            Modifier
                .fillMaxWidth()
                .constrainAs(title) { top.linkTo(image.bottom, 15.dp) }
                .padding(horizontal = 15.dp)
        ) {

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BottomBarLineColor)
                    .fillMaxWidth()
                    .height(20.dp)
            )

            Spacer(modifier = Modifier.size(10.dp, 12.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BottomBarLineColor)
                    .width(200.dp)
                    .height(15.dp)
            )

            Spacer(modifier = Modifier.size(10.dp, 5.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BottomBarLineColor)
                    .width(200.dp)
                    .height(15.dp)
            )

            Spacer(modifier = Modifier.size(10.dp, 12.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(BottomBarLineColor)
                    .width(200.dp)
                    .height(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.size(10.dp, 20.dp))
}