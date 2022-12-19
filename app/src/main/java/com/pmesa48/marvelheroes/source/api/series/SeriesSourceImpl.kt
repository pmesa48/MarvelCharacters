package com.pmesa48.marvelheroes.source.api.series

import com.pmesa48.marvelheroes.source.api.common.BaseService
import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.SeriesDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper

class SeriesSourceImpl(
    private val seriesApi: SeriesApi
) : BaseService(), SeriesSource {

    override suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ): ServerResponse<ServiceWrapper<List<SeriesDto>>> {
        return safeCall { seriesApi.get(characterId, limit, offset) }
    }
}