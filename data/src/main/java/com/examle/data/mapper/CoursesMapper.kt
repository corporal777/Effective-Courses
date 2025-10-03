package com.examle.data.mapper

import com.examle.common.DateUtils.formatToDefaultDayMonthYearDate
import com.examle.data.db.dbo.CourseDbo
import com.examle.data.models.CourseResponse
import com.examle.data.models.CoursesResponse
import com.examle.domain.model.CourseModel
import com.examle.domain.model.MyCourseModel


internal fun CourseResponse.mapToCourseModelFromResponse(hasLike: Boolean): CourseModel {
    return CourseModel(
        id = id,
        title = title,
        text = text,
        price = "$price ₽",
        rate = rate,
        startDate = startDate.formatToDefaultDayMonthYearDate() ?: startDate,
        isLiked = hasLike,
        publishDate = publishDate
    )
}

internal fun CourseDbo.mapToCourseModelFromDbo(): CourseModel {
    return CourseModel(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        isLiked = true,
        publishDate = publishDate
    )
}

internal fun CourseModel.mapToDboFromCourseModel(): CourseDbo {
    return CourseDbo(
        id = id,
        title = title,
        text = text,
        price = price,
        rate = rate,
        startDate = startDate,
        publishDate = publishDate
    )
}

internal fun CourseResponse.mapToMyCourseFromCourseModel(): MyCourseModel {
    return MyCourseModel(
        id,
        title,
        rate,
        startDate.formatToDefaultDayMonthYearDate() ?: startDate,
        false,
        "50%",
        Pair(22, 44)
    )
}