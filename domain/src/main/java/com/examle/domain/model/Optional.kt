package com.examle.domain.model

data class Optional<out T>(
    val value: T? = null
)

fun <T> T?.asOptional() = Optional(this)