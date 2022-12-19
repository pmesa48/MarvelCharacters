package com.pmesa48.marvelheroes.source.api.series

import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.SeriesDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper

interface SeriesSource {

    suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ) : ServerResponse<ServiceWrapper<List<SeriesDto>>>

}
