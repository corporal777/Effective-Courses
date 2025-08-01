package com.examle.effectivecourses.ui.home

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.examle.effectivecourses.R
import com.effective.networkmodule.model.CourseModel
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteLoadingBackColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.AppTextFieldBig
import com.examle.effectivecourses.utils.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.effectivecourses.utils.ShimmerItem
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (id: String) -> Unit
) {

    val uiState = viewModel.courses.value

    Log.e("RATE DATA", uiState.toString())

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
            { viewModel.searchCourse(it) },
            { viewModel.sortCoursesByDate(it) }
        )

        Column(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(content) { top.linkTo(spacer.bottom) }) {

            uiState.forEach { course ->
                if (course == null) ShimmerItem()
                else CourseItem(
                    viewModel,
                    course,
                    { viewModel.addCourseToFavorite(it) },
                    { onItemClick.invoke(it) })
            }
        }
    }
}


@Composable
private fun ConstraintLayoutScope.SearchItem(
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


@Composable
private fun CourseItem(
    viewModel: HomeViewModel,
    course: CourseModel,
    onFavoriteClick: (model: CourseModel) -> Unit,
    onItemClick: (id: String) -> Unit
) {

    var courseTitle by remember { mutableStateOf("") }
    courseTitle = course.title

    var courseText by remember { mutableStateOf("") }
    courseText = course.text

    var coursePrice by remember { mutableStateOf("") }
    coursePrice = course.price

    var rate by remember { mutableStateOf("") }
    rate = course.rate

    var startDate by remember { mutableStateOf("") }
    startDate = course.startDate

    var isLiked by remember { mutableStateOf(false) }
    isLiked = course.hasLike

    var isLoading by remember { mutableStateOf(false) }
    isLoading = if (viewModel.buttonLoading.value.first.toString() == course.id)
        viewModel.buttonLoading.value.second
    else false

    ConstraintLayout(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CourseItemColor)
            .clickable(Color.White) { onItemClick.invoke(course.id) }
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
                .clickable(Color.White) { onFavoriteClick.invoke(course) }
                .padding(8.dp)
        )


        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp)
                    .constrainAs(progress) {
                        top.linkTo(favorite.top)
                        bottom.linkTo(favorite.bottom)
                        start.linkTo(favorite.start)
                        end.linkTo(favorite.end)
                    }
                    .background(CourseFavoriteLoadingBackColor)
                    .padding(5.dp),
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


@Composable
fun TextWithIcon(rate: String, modifier: Modifier) {
    val modId = "modIcon"
    val text = buildAnnotatedString {
        appendInlineContent(modId, "[icon]")
        append(rate)

    }
    val inlineContent = mapOf(
        Pair(
            modId,
            InlineTextContent(Placeholder(18.sp, 18.sp, PlaceholderVerticalAlign.Center)) {
                Icon(
                    painterResource(R.drawable.ic_star_fill),
                    "",
                    tint = CourseMoreTextColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = (3).dp, top = 2.dp, end = 2.dp)
                )
            }
        )
    )

    Text(
        text = text,
        inlineContent = inlineContent,
        color = Color.White,
        fontSize = 14.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Normal,
        modifier = modifier
    )
}

enum class SORT {
    DATE, NONE
}