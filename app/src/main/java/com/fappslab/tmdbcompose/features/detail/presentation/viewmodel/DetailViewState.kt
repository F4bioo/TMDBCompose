package com.fappslab.tmdbcompose.features.detail.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.data.common.constant.Util.INIT_STRING
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.detail.domain.model.Pack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailViewState(
    val detail: Detail = Detail(),
    val errorMessage: String = INIT_STRING,
    val shouldCollapseText: Boolean = true,
    val shouldShowLoading: Boolean = false,
    val isFavoriteChecked: Boolean = false,
    val movies: Flow<PagingData<Movie>> = emptyFlow()
) {

    fun setSuccessState(
        movies: Flow<PagingData<Movie>>,
        successPair: Pair<Pack, Boolean>
    ): DetailViewState {
        val (pack, isChecked) = successPair
        return copy(
            detail = pack.detail,
            movies = movies,
            isFavoriteChecked = isChecked
        )
    }
}
