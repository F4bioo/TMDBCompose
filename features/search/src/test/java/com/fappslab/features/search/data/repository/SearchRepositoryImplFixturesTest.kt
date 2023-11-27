package com.fappslab.features.search.data.repository

import androidx.annotation.VisibleForTesting
import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.core.data.remote.model.extension.toMovies
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToModel
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToString
import com.fappslab.libraries.arch.testing.rules.RemoteTestRule
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

internal const val SEARCH_SUCCESS_RESPONSE = "search_success.json"
internal const val SEARCH_FAILURE_RESPONSE = "search_failure.json"

@VisibleForTesting
internal fun RemoteTestRule.toServerSuccessResponse(fileBlock: () -> String) {
    val body = readFromJSONToString(fileBlock())
    mockWebServerResponse(body, HttpURLConnection.HTTP_OK)
}

@VisibleForTesting
internal fun RemoteTestRule.toServerFailureResponse(fileBlock: () -> String) {
    val body = readFromJSONToString(fileBlock())
    mockWebServerResponse(body, HttpsURLConnection.HTTP_BAD_REQUEST)
}

@VisibleForTesting
internal object ResponseFactory {
    private val movieResponse = readFromJSONToModel<MovieResponse>(SEARCH_SUCCESS_RESPONSE)
    val movies = movieResponse.result.toMovies()
}
