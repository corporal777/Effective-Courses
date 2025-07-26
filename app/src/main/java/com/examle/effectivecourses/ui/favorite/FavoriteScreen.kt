package com.examle.effectivecourses.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.examle.effectivecourses.R
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.home.TextWithIcon
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.effectivecourses.utils.ShimmerItem
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    padding: PaddingValues,
    viewModel: FavoriteViewModel = koinViewModel(),
    onItemClick: (id: String) -> Unit
) {

    val uiState = viewModel.courses.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, padding.calculateTopPadding() + 30.dp, 20.dp, 120.dp)
    ) {

        HeaderItem()

        uiState.forEach { course ->
            if (course == null) ShimmerItem()
            else CourseItem(viewModel, course) { viewModel.removeFavoriteCourse(it) }
        }
    }
}


@Composable
private fun HeaderItem() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Избранное",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start
    )

    Spacer(modifier = Modifier.size(10.dp, 20.dp))
}

@Composable
private fun CourseItem(
    viewModel: FavoriteViewModel,
    course: CourseModel,
    onFavoriteClick: (model: CourseModel) -> Unit
) {

    var rate by remember { mutableStateOf("") }
    rate = course.rate

    var startDate by remember { mutableStateOf("") }
    startDate = course.startDate

    var courseTitle by remember { mutableStateOf("") }
    courseTitle = course.title

    var courseText by remember { mutableStateOf("") }
    courseText = course.text

    var coursePrice by remember { mutableStateOf("") }
    coursePrice = course.price

    val buttonState by viewModel.buttonLoading
    var isLoading by rememberSaveable { mutableStateOf(false) }
    isLoading = if (buttonState.first.toString() == course.id) buttonState.second else false

    var isLiked by rememberSaveable { mutableStateOf(false) }
    isLiked = course.hasLike

    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CourseItemColor)
            .clickable(Color.White) { }
            .padding(bottom = 15.dp)
    ) {
        val (image, title, text, price, more, moreIcon, favorite, favBlur, date, progress) = createRefs()

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
            painter = painterResource(if (isLiked) R.drawable.ic_favorite_fill else R.drawable.ic_favorite),
            contentDescription = "",
            tint = if (isLiked) CourseMoreTextColor else CourseFavoriteIconColor,
            modifier = Modifier
                .clip(CircleShape)
                .size(35.dp)
                .background(CourseFavoriteBackColor)
                .constrainAs(favorite) {
                    top.linkTo(image.top, 10.dp)
                    end.linkTo(image.end, 10.dp)
                }
                .clickable(Color.White, enabled = !isLoading) { onFavoriteClick.invoke(course) }
                .padding(8.dp)
                .alpha(if (isLoading) 0f  else 1f)
        )

        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(35.dp)
                    .constrainAs(progress) {
                        top.linkTo(favorite.top)
                        bottom.linkTo(favorite.bottom)
                        start.linkTo(favorite.start)
                        end.linkTo(favorite.end)
                    }.padding(5.dp),
                color = CourseFavoriteIconColor,
                trackColor = Color.Transparent,
                strokeWidth = (2).dp,
                strokeCap = StrokeCap.Round
            )
        }


        TextWithIcon(rate, modifier = Modifier
            .clip(CircleShape)
            .background(CourseFavoriteBackColor)
            .constrainAs(favBlur) {
                start.linkTo(image.start, 10.dp)
                bottom.linkTo(image.bottom, 10.dp)
            }
            .padding(start = 7.dp, end = 10.dp))


        Text(
            text = startDate.formatToDefaultDayMonthYearDate() ?: startDate,
            color = Color.White,
            fontSize = 14.sp,
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
            text = courseTitle,
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
            text = courseText,
            color = CourseItemTextColor,
            fontSize = 15.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Normal,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 15.dp, end = 15.dp)
                .alpha(0.7f)
                .constrainAs(text) { top.linkTo(title.bottom) }
        )

        Text(
            text = "$coursePrice ₽",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 20.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = 15.dp)
                .constrainAs(price) {
                    start.linkTo(parent.start)
                    top.linkTo(text.bottom)
                }
        )

        Text(
            text = "Подробнее",
            color = CourseMoreTextColor,
            fontSize = 15.sp,
            fontFamily = TextUtils.robotoFont,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 20.sp,
            modifier = Modifier
                .constrainAs(more) {
                    end.linkTo(moreIcon.start, 5.dp)
                    baseline.linkTo(price.baseline)
                }
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
