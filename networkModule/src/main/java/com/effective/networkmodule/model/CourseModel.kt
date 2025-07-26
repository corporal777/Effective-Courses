package com.effective.networkmodule.model

data class CourseModel(
    val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
) {

}