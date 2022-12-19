package com.pmesa48.marvelheroes.repository.series

import com.pmesa48.marvelheroes.repository.common.PaginatedParam

data class GetSeriesParam(
    val characterId: String, val lastVisible: Int
) : PaginatedParam(characterId, lastVisible)
