package com.fappslab.features.detail.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.core.domain.model.Detail
import com.fappslab.libraries.design.component.preview.detailDataPreview
import com.fappslab.tmdbcompose.features.detail.R

@Composable
fun InfoGroupView(
    modifier: Modifier = Modifier,
    detail: Detail
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InfoView(
            tile = stringResource(id = R.string.vote_average),
            data = detail.voteAverage.toString()
        )
        InfoView(
            tile = stringResource(id = R.string.duration),
            data = stringResource(id = R.string.duration_minutes, detail.duration.toString())
        )
        InfoView(
            tile = stringResource(id = R.string.release_date),
            data = detail.releaseDate
        )
    }
}

@Composable
fun InfoView(
    tile: String,
    data: String
) {
    Column {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = tile,
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp, letterSpacing = 1.sp),
            color = Color.DarkGray
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = data,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
            color = Color.DarkGray
        )
    }
}

@Preview
@Composable
fun InfoGroupViewPreview() {
    InfoGroupView(
        modifier = Modifier.fillMaxWidth(),
        detail = detailDataPreview()
    )
}
