package com.fappslab.tmdbcompose.features.detail.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fappslab.features.detail.presentation.component.StarView

@Composable
fun RatingView(
    modifier: Modifier = Modifier,
    rating: Float,
    color: Color = Color.Yellow
) {
    Row(modifier = modifier.wrapContentSize()) {
        (1..5).forEach { step ->
            val stepRating = when {
                rating > step -> 1f
                step.rem(rating) < 1 -> rating - (step - 1f)
                else -> 0f
            }
            StarView(stepRating, color)
        }
    }
}
