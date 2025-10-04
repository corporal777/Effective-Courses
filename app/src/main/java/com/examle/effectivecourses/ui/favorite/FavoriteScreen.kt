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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.examle.common.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.domain.model.CourseModel
import com.examle.domain.model.DataState
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.components.CourseItem
import com.examle.effectivecourses.ui.components.ProgressDialog
import com.examle.effectivecourses.ui.components.TextWithIcon
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.ui.components.ShimmerItem
import com.examle.effectivecourses.ui.components.TextSemibold
import com.examle.effectivecourses.utils.TextUtils
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    padding: PaddingValues,
    viewModel: FavoriteViewModel = koinViewModel()
) {

    val uiState by viewModel.courses.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(20.dp, padding.calculateTopPadding() + 30.dp, 20.dp, 120.dp)
    ) {

        HeaderItem()

        when (uiState) {
            DataState.Error -> {}
            DataState.Loading -> ShimmerItem()
            else -> {
                (uiState as DataState.Success<List<CourseModel>>).data.forEach { course ->
                    CourseItem(
                        course,
                        onFavoriteClick = { viewModel.removeFavoriteCourse(it) },
                        onItemClick = { }
                    )
                }
            }
        }
    }
}


@Composable
private fun HeaderItem() {
    TextSemibold(
        modifier = Modifier.fillMaxWidth(),
        text = "Избранное",
        color = CourseItemTextColor,
        fontSize = 23.sp,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.size(10.dp, 20.dp))
}
