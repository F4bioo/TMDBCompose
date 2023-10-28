package com.fappslab.libraries.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fappslab.libraries.design.theme.red
import com.fappslab.libraries.design.theme.white
import com.fappslab.tmdbcompose.libraries.design.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    message: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp,
            color = white,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = red)
        ) {
            Text(
                text = stringResource(id = R.string.common_try_again),
                color = white
            )
        }
    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    ErrorView(
        message = stringResource(id = R.string.common_error_title),
        onClick = {}
    )
}
