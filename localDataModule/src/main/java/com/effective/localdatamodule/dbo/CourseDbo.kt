package com.effective.localdatamodule.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseDbo(
    @PrimaryKey
    val id: String,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val publishDate: String
)