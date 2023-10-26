package com.fappslab.tmdbcompose.features.detail.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.tmdbcompose.R

@Composable
fun SynopsisView(
    modifier: Modifier = Modifier,
    shouldCollapseText: Boolean,
    overview: String,
    onCollapse: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.synopsis),
            color = Color.White,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        val (maxLines, overflow) = if (shouldCollapseText) {
            3 to TextOverflow.Ellipsis
        } else Int.MAX_VALUE to TextOverflow.Clip

        Text(
            modifier = Modifier.clickable { onCollapse() },
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
    SynopsisView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shouldCollapseText = true,
        overview = "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
        onCollapse = {}
    )
}
