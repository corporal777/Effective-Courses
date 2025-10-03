package com.examle.effectivecourses.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.examle.effectivecourses.ui.theme.BottomBarLineColor
import com.examle.effectivecourses.ui.theme.CourseItemTextColor

@Composable
fun ProgressDialog(show: Boolean) {
    if (show) {
        Dialog(onDismissRequest = { }) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .width(70.dp)
                    .height(70.dp)
                    .background(BottomBarLineColor),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(45.dp),
                    color = CourseItemTextColor,
                    trackColor = Color.Transparent,
                    strokeWidth = (4).dp,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}