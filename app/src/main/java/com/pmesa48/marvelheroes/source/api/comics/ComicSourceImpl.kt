package com.pmesa48.marvelheroes.source.api.comics

import android.util.Log
import com.pmesa48.marvelheroes.source.api.common.BaseService
import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.ComicDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper

class ComicSourceImpl(
    private val comicApi: ComicApi
) : BaseService(), ComicSource {

    override suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ): ServerResponse<ServiceWrapper<List<ComicDto>>> =
        safeCall {
            comicApi.getComicsByCharacterId(characterId, limit, offset)
        }
}