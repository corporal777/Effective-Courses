package com.examle.effectivecourses.dataSource.model

import com.effective.networkmodule.model.CourseModel

class MyCourseModel(
    val id: String,
    val title: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val percent: Int,
    val lessons: Pair<Int, Int>
) {
    companion object {
        fun createFromRemote(remote: CourseModel, ): MyCourseModel {
            return MyCourseModel(
                remote.id,
                remote.title,
                remote.rate,
                remote.startDate,
                remote.hasLike,
                50,
                Pair(22, 44)
            )
        }
    }
}