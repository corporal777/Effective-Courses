package com.examle.effectivecourses.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.components.AppTextFieldBig
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun ConstraintLayoutScope.SearchItem(
    input: ConstrainedLayoutReference,
    filter: ConstrainedLayoutReference,
    sort: ConstrainedLayoutReference,
    sortIcon: ConstrainedLayoutReference,
    spacer: ConstrainedLayoutReference,
    onTextChange: (text: String) -> Unit,
    onSort: (isSorted: Boolean) -> Unit
) {
    var isSorted by remember { mutableStateOf(false) }

    AppTextFieldBig(
        Modifier
            .height(55.dp)
            .fillMaxWidth()
            .constrainAs(input) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(filter.start)
                width = Dimension.fillToConstraints
            }
            .padding(end = 15.dp), onTextChange
    )

    Icon(
        painter = painterResource(R.drawable.ic_filter),
        contentDescription = "",
        tint = Color.White,
        modifier = Modifier
            .clip(CircleShape)
            .size(55.dp)
            .background(CourseItemColor)
            .constrainAs(filter) {
                top.linkTo(input.top)
                bottom.linkTo(input.bottom)
                end.linkTo(parent.end)
                start.linkTo(input.end)
            }
            .padding(15.dp)
    )

    Text(
        text = if (!isSorted) "По дате добавления" else "Сбросить",
        color = CourseMoreTextColor,
        fontSize = 16.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(end = 5.dp)
            .constrainAs(sort) {
                top.linkTo(input.bottom, 15.dp)
                end.linkTo(sortIcon.start)
            }
            .clickable(Color.White) {
                isSorted = !isSorted
                onSort.invoke(isSorted)
            }
    )

    Icon(
        painter = painterResource(R.drawable.ic_arrow_down_up),
        contentDescription = "",
        tint = CourseMoreTextColor,
        modifier = Modifier.constrainAs(sortIcon) {
            end.linkTo(parent.end)
            top.linkTo(sort.top)
            bottom.linkTo(sort.bottom)
        })

    Spacer(modifier = Modifier
        .fillMaxWidth()
        .height(20.dp)
        .constrainAs(spacer) { top.linkTo(sort.bottom) })
}