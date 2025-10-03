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

class FavoriteInteractor (private val repository: FavoriteRepository) {

    suspend fun getFavoriteCourses(): Result<List<CourseModel>> {
        return repository.getFavoriteCourses().let {
            if (it.isNotEmpty()) Result.success(it)
            else Result.failure(NullPointerException())
        }
    }

    fun removeCourseFavourite(model: CourseModel): Flow<CourseFavoriteModel> {
        return flow<CourseFavoriteModel> {
            val result = repository.removeCourseFavorite(model)

            delay(500)
            emit(result)
        }
    }


}