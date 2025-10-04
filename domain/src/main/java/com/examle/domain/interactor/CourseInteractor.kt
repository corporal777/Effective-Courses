package com.examle.domain.interactor

import com.examle.common.DateUtils.timeMillis
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.repository.CoursesRepository
import com.examle.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class CourseInteractor(
    private val coursesRepository: CoursesRepository,
    private val favoriteRepository: FavoriteRepository
) {

    private var coursesList = mutableListOf<CourseModel>()
    private var isSorted = false

    suspend fun getCoursesList(): Result<List<CourseModel>> {
        return coursesRepository.getCourses().let {
            coursesList = it.toMutableList()
            if (it.isNotEmpty()) Result.success(it)
            else Result.failure(NullPointerException())
        }
    }

    suspend fun getCoursesLocal(): List<CourseModel> {
        return withContext(Dispatchers.IO) {
            coursesList.let {
                if (isSorted) it.sortedByDescending { it.publishDate.timeMillis() }
                else it
            }
        }
    }

    fun setCoursesSorted(isSorted: Boolean) {
        this.isSorted = isSorted
    }


    fun addOrRemoveCourseFavourite(model: CourseModel): Flow<List<CourseModel>> {
        return flow<CourseFavoriteModel> {
            val result = if (!model.isLiked) favoriteRepository.addCourseFavorite(model)
            else favoriteRepository.removeCourseFavorite(model)
            emit(result)
        }.flatMapConcat { favorite ->
            val item = coursesList.find { it.id == model.id }
            val index = coursesList.indexOf(item)
            item?.let { coursesList.set(index, it.copy(isLiked = favorite.isFavorite)) }

            delay(500)
            flowOf(coursesList)
        }
    }


}