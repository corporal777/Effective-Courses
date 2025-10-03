package com.examle.data.repository

import com.examle.common.exceptions.RoomDbException
import com.examle.data.api.ApiService
import com.examle.data.db.CourseDataSource
import com.examle.data.mapper.mapToCourseModelFromResponse
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.repository.CoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: CourseDataSource
) : CoursesRepository {


    override suspend fun getCourses(): List<CourseModel> {
        return withContext(Dispatchers.IO){
            val local = dataSource.getFavoriteCourses()
            val remote = apiService.getCourses()

            if (remote.isSuccessful && remote.body() != null){
                remote.body()!!.courses.map { response ->
                    val hasLike = if (local.isEmpty()) false else local.any { it.id == response.id }
                    response.mapToCourseModelFromResponse(hasLike)
                }
            }
            else emptyList()
        }
    }

    override suspend fun getCourseById(id: String): CourseModel? {
        return withContext(Dispatchers.IO){
            val remote = apiService.getCourses()
            if (remote.isSuccessful && remote.body() != null){
                val course = remote.body()?.courses?.find { x -> x.id == id }
                val local = dataSource.getCourseById(id)

                course?.mapToCourseModelFromResponse(local != null)
            } else null
        }
    }

}