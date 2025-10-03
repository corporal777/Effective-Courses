package com.examle.domain.interactor

import com.examle.common.DateUtils.timeMillis
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.repository.CoursesRepository
import com.examle.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class CourseDetailInteractor(
    private val courseRepository: CoursesRepository,
    private val favoriteRepository: FavoriteRepository
) {

    suspend fun getCourseById(id : String): Result<CourseModel> {
        return courseRepository.getCourseById(id).let {
            if (it == null) Result.failure(NullPointerException())
            else Result.success(it)
        }
    }

    fun addOrRemoveCourseFavourite(model: CourseModel): Flow<CourseModel> {
        return flow<CourseFavoriteModel> {
            val result = if (!model.isLiked) favoriteRepository.addCourseFavorite(model)
            else favoriteRepository.removeCourseFavorite(model)
            emit(result)
        }.flatMapConcat { favorite ->

            delay(500)
            flowOf(model.copy(isLiked = favorite.isFavorite))
        }
    }


}