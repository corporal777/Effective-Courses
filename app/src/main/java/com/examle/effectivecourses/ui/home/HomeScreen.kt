package com.examle.effectivecourses.ui.home

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.examle.domain.model.CourseModel
import com.examle.domain.model.DataState
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.components.TextWithIcon
import com.examle.effectivecourses.ui.home.components.SearchItem
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.components.ProgressDialog
import com.examle.effectivecourses.ui.components.ShimmerItem
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (String) -> Unit
) {

    val uiState by viewModel.courses.collectAsState(DataState.Loading)

    val isLoadingState by viewModel.loading.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(isLoadingState) { isLoading = isLoadingState }

    ProgressDialog(isLoading)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, padding.calculateTopPadding() + 20.dp, 20.dp, 120.dp)
    ) {
        val (input, filter, sort, sortIcon, spacer, content) = createRefs()

        SearchItem(
            input, filter, sort, sortIcon, spacer,
            {  },
            { viewModel.sortCoursesByDate(it) }
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(content) { top.linkTo(spacer.bottom) }) {

            when (uiState) {
                DataState.Error -> {}
                DataState.Loading -> ShimmerItem()
                else -> {
                    (uiState as DataState.Success<List<CourseModel>>).data.forEach { course ->
                        CourseItem(
                            course,
                            onItemClick = { onItemClick.invoke(it) },
                            onFavoriteClick = { viewModel.addCourseToFavorite(it) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CourseItem(
    course: CourseModel,
    onFavoriteClick: (model: CourseModel) -> Unit,
    onItemClick: (String) -> Unit
) {

    var isLiked by remember { mutableStateOf(false) }
    isLiked = course.isLiked


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


        Text(
            text = course.startDate,
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
            text = course.text,
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
            text = course.price,
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