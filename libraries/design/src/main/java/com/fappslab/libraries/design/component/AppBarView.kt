package com.fappslab.libraries.design.component

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource

const val APP_BAR_VIEW_TAG = "AppBarView"

@Composable
fun AppBarView(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Black
) {
    TopAppBar(
        modifier = modifier.testTag(APP_BAR_VIEW_TAG),
        title = {
            Text(
                text = stringResource(id = titleRes),
                color = textColor
            )
        },
        backgroundColor = backgroundColor
    )
}
