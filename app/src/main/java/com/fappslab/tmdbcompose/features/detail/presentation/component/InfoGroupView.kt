package com.fappslab.tmdbcompose.features.detail.presentation.component

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
import com.fappslab.tmdbcompose.R
import com.fappslab.tmdbcompose.core.domain.model.Detail

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
    val detail = Detail(
        id = 1,
        title = "Avatar: The Way of Water",
        genres = listOf("Science Fiction", "Adventure", "Action"),
        overview = "Set more than a decade after the events of the first film, learn the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive, and the tragedies they endure.",
        imageUrl = "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg",
        releaseDate = "2022-12-14",
        voteAverage = 7.65,
        duration = 192,
        voteCount = 9977
    )
    InfoGroupView(
        modifier = Modifier.fillMaxWidth(),
        detail = detail
    )
}
