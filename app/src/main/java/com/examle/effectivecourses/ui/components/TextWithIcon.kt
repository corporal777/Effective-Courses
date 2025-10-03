package com.examle.effectivecourses.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.R
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor
import com.examle.effectivecourses.utils.TextUtils

@Composable
fun TextWithIcon(rate: String, modifier: Modifier) {
    val modId = "modIcon"
    val text = buildAnnotatedString {
        appendInlineContent(modId, "[icon]")
        append(rate)

    }
    val inlineContent = mapOf(
        Pair(
            modId,
            InlineTextContent(Placeholder(18.sp, 18.sp, PlaceholderVerticalAlign.Center)) {
                Icon(
                    painterResource(R.drawable.ic_star_fill),
                    "",
                    tint = CourseMoreTextColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = (3).dp, top = 2.dp, end = 2.dp)
                )
            }
        )
    )

    Text(
        text = text,
        inlineContent = inlineContent,
        color = Color.White,
        fontSize = 14.sp,
        fontFamily = TextUtils.robotoFont,
        fontWeight = FontWeight.Normal,
        modifier = modifier
    )
}