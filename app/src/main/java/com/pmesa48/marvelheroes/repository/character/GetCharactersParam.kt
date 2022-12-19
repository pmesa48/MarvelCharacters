package com.pmesa48.marvelheroes.repository.character

import com.pmesa48.marvelheroes.repository.common.PaginatedParam

class GetCharactersParam(
    val query: String,
    lastVisible: Int
) : PaginatedParam(query, lastVisible)