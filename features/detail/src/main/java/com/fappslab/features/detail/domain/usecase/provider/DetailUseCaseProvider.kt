package com.fappslab.features.detail.domain.usecase.provider

import com.fappslab.core.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.core.domain.usecase.IsFavoriteUseCase
import com.fappslab.core.domain.usecase.SetFavoriteUseCase
import com.fappslab.features.detail.domain.usecase.GetMovieDetailUseCase
import javax.inject.Inject

internal data class DetailUseCaseProvider @Inject constructor(
    val getMovieDetailUseCase: GetMovieDetailUseCase,
    val isFavoriteUseCase: IsFavoriteUseCase,
    val setFavoriteUseCase: SetFavoriteUseCase,
    val deleteFavoriteUseCase: DeleteFavoriteUseCase,
)
