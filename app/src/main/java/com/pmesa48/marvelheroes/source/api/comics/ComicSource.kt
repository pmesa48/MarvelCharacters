package com.pmesa48.marvelheroes.source.api.comics

import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.ComicDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper

interface ComicSource {
    suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ) : ServerResponse<ServiceWrapper<List<ComicDto>>>
}
