package com.examle.common

import java.util.regex.Pattern

object AuthUtils {

    private val EMAIL_PATTERN = Pattern.compile(
        "[a-zA-Z\\($CYRILLIC)\\($SYMBOLS)0-9]{1,256}" +
                "\\@[a-zA-Z\\($CYRILLIC)0-9]{0,64}" +
                "(\\.[a-zA-Z\\($CYRILLIC)0-9]{0,25})+"
    )

    fun isValidEmail(email: CharSequence) = EMAIL_PATTERN.matcher(email).matches()
}