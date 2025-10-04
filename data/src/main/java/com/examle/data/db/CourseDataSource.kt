package com.examle.data.db

import com.examle.data.db.dao.CourseDao
import com.examle.data.db.dbo.CourseDbo
import com.examle.data.mapper.mapToDboFromCourseModel
import com.examle.domain.model.CourseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseDataSource(private val courseDao: CourseDao, ) {

    suspend fun addCourse(dbo: CourseDbo): Boolean {
        return withContext(Dispatchers.IO){
            courseDao.insert(dbo) > 0
        }
    }

    suspend fun deleteCourse(id : String) : Boolean {
        return withContext(Dispatchers.IO){
            courseDao.deleteById(id) > 0
        }
    }


    suspend fun getFavoriteCourses() = courseDao.getAll()

    suspend fun getCourseById(id : String) : CourseDbo? {
        return courseDao.getById(id)
    }
}