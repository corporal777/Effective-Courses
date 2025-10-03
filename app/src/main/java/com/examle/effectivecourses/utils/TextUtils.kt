package com.examle.effectivecourses.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.examle.effectivecourses.R
import java.util.regex.Pattern

object TextUtils {

    val robotoFont = FontFamily(
        Font(R.font.roboto_regular, FontWeight.Normal),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_semibold, FontWeight.SemiBold),
    )
}