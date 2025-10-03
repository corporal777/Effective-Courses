package com.examle.domain.model

class MyCourseModel(
    val id: String,
    val title: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val percent: String,
    val lessons: Pair<Int, Int>
) {
}