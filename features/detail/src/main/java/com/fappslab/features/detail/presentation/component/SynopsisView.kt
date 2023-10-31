package com.fappslab.features.detail.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.features.detail.R

internal const val SYNOPSIS_VIEW_TAG = "SynopsisView"
internal const val ARROW_VIEW_TAG = "ArrowView"

@Composable
internal fun SynopsisView(
    modifier: Modifier = Modifier,
    shouldCollapseText: Boolean,
    overview: String,
    onCollapse: (isExpanded: Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .testTag(SYNOPSIS_VIEW_TAG)
            .padding(8.dp)
            .clickable {
                onCollapse(shouldCollapseText.not())
            },
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        ConstraintLayout {
            val (title, arrow) = createRefs()

            Text(
                text = stringResource(id = R.string.synopsis),
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            val imageVector = if (shouldCollapseText) {
                Icons.Default.KeyboardArrowDown
            } else Icons.Default.KeyboardArrowUp

            Icon(
                modifier = Modifier
                    .testTag(imageVector.name)
                    .constrainAs(arrow) {
                        start.linkTo(title.end)
                        top.linkTo(title.top)
                        bottom.linkTo(title.bottom)
                    },
                imageVector = imageVector,
                tint = Color.White,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val (maxLines, overflow) = if (shouldCollapseText) {
            3 to TextOverflow.Ellipsis
        } else Int.MAX_VALUE to TextOverflow.Clip

        Text(
            text = overview,
            color = Color.DarkGray,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}

@Preview
@Composable
fun SynopsisViewPreview() {
    val detail = detailDataPreview()

    SynopsisView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shouldCollapseText = true,
        overview = detail.overview,
        onCollapse = {}
    )
}
