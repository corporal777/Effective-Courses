package com.examle.data.repository

import com.examle.common.exceptions.RoomDbException
import com.examle.data.api.ApiService
import com.examle.data.data.AppData
import com.examle.data.db.CourseDataSource
import com.examle.data.mapper.mapToCourseModelFromResponse
import com.examle.data.mapper.mapToMyCourseFromCourseModel
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.model.MyCourseModel
import com.examle.domain.repository.CoursesRepository
import com.examle.domain.repository.MyCoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCoursesRepositoryImpl(
    private val apiService: ApiService
) : MyCoursesRepository {

    override suspend fun getMyCourses(): List<MyCourseModel> {
        return withContext(Dispatchers.IO) {
            val remote = apiService.getCourses()

            if (remote.isSuccessful && remote.body() != null) {
                remote.body()!!.courses.subList(0, 2).map { response ->
                    response.mapToMyCourseFromCourseModel()
                }
            } else emptyList()
        }
    }

}