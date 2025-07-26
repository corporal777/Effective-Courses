package com.examle.effectivecourses.utils

import android.util.Log
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examle.effectivecourses.R
import com.examle.effectivecourses.extensions.clickable
import com.examle.effectivecourses.ui.theme.CourseFavoriteBackColor
import com.examle.effectivecourses.ui.theme.CourseFavoriteIconColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor
import com.examle.effectivecourses.ui.theme.CourseMoreTextColor

@Composable
fun LoadingButton(
    text: String,
    backColor: Color,
    loading: Boolean = false,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    var isLoading by remember { mutableStateOf(false) }
    isLoading = loading

    var isEnabled by remember { mutableStateOf(true) }
    isEnabled = enabled

    Box(
        modifier = modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(50.dp)
            .alpha(if (isEnabled) 1f else 0.5f)
            .background(backColor)
            .clickable(Color.White, !isLoading && isEnabled) { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLoading) "" else text,
            color = CourseItemTextColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(35.dp),
                color = CourseItemTextColor,
                trackColor = Color.Transparent,
                strokeWidth = (3.5).dp,
                strokeCap = StrokeCap.Round
            )
        }
    }

}