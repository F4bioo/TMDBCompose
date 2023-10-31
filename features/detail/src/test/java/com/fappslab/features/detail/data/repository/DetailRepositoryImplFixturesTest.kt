package com.fappslab.features.detail.data.repository

import androidx.annotation.VisibleForTesting
import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.core.data.remote.model.extension.toMovies
import com.fappslab.features.detail.data.model.DetailResponse
import com.fappslab.features.detail.data.model.extension.toDetail
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToModel
import com.fappslab.libraries.arch.jsonhandle.readFromJSONToString
import com.fappslab.libraries.arch.testing.rules.RemoteTestRule
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

internal const val DETAIL_SUCCESS_RESPONSE = "detail_success.json"
internal const val DETAIL_FAILURE_RESPONSE = "detail_failure.json"

internal const val SIMILAR_SUCCESS_RESPONSE = "similar_success.json"
internal const val SIMILAR_FAILURE_RESPONSE = "similar_failure.json"

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

internal object ResponseFactory {
    private val detailResponse = readFromJSONToModel<DetailResponse>(DETAIL_SUCCESS_RESPONSE)
    val detail = detailResponse.toDetail()

    private val movieResponse = readFromJSONToModel<MovieResponse>(SIMILAR_SUCCESS_RESPONSE)
    val movies = movieResponse.result.toMovies()
}
