package com.examle.effectivecourses.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.examle.domain.model.CourseModel
import com.examle.domain.model.DataState
import com.examle.effectivecourses.ui.components.CourseItem
import com.examle.effectivecourses.ui.components.ShimmerItem
import com.examle.effectivecourses.ui.home.components.SearchItem
import com.examle.effectivecourses.ui.theme.AppBackgroundColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
    onItemClick: (String) -> Unit
) {

    val uiState by viewModel.courses.collectAsState(DataState.Loading)

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
            { },
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

