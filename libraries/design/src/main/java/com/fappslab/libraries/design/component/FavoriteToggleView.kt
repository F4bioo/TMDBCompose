package com.fappslab.libraries.design.component

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteToggleView(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (isChecked: Boolean) -> Unit
) {
    val (imageVector, tint) = checkedHandler(isChecked)

    IconToggleButton(
        modifier = modifier,
        checked = isChecked,
        onCheckedChange = onCheckedChange
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = tint
        )
    }
}

private fun checkedHandler(isChecked: Boolean): Pair<ImageVector, Color> {
    return if (isChecked) {
        Icons.Default.Favorite to Color.Red
    } else Icons.Default.FavoriteBorder to Color.LightGray
}

@Preview
@Composable
fun FavoriteToggleViewPreview() {
    FavoriteToggleView(
        isChecked = true,
        onCheckedChange = {}
    )
}
