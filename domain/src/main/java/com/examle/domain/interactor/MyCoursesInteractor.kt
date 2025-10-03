package com.examle.domain.interactor

import com.examle.common.DateUtils.timeMillis
import com.examle.domain.model.CourseFavoriteModel
import com.examle.domain.model.CourseModel
import com.examle.domain.model.MyCourseModel
import com.examle.domain.repository.CoursesRepository
import com.examle.domain.repository.LoginRepository
import com.examle.domain.repository.MyCoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class MyCoursesInteractor(
    private val repository: MyCoursesRepository,
    private val authRepository: LoginRepository
) {


    suspend fun getMyCourses(): Result<List<MyCourseModel>> {
        return repository.getMyCourses().let {
            if (it.isNotEmpty()) Result.success(it)
            else Result.failure(NullPointerException())
        }
    }

    fun logoutFromProfile(): Flow<Unit> {
        return flow {
            authRepository.logoutProfile()
            delay(1000)
            emit(Unit)
        }
    }

}