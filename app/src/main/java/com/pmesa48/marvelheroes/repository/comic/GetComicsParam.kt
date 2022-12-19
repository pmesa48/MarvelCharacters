package com.pmesa48.marvelheroes.repository.comic

import com.pmesa48.marvelheroes.repository.common.PaginatedParam

data class GetComicsParam(
    val characterId: String,
    val lastVisible: Int
) : PaginatedParam(characterId, lastVisible)
