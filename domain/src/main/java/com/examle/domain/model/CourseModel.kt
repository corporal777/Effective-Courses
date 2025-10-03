package com.examle.domain.model

data class CourseModel(
    val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val isLiked: Boolean,
    val publishDate: String
)