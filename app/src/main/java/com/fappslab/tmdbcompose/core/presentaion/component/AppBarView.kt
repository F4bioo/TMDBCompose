package com.fappslab.tmdbcompose.core.presentaion.component

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun AppBarView(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = titleRes),
                color = textColor
            )
        },
        backgroundColor = backgroundColor
    )
}
