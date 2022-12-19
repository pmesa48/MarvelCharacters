package com.pmesa48.marvelheroes.repository.series

import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.repository.common.PaginatedRepository
import com.pmesa48.marvelheroes.source.api.dto.SeriesDto
import com.pmesa48.marvelheroes.source.api.series.SeriesSource
import com.pmesa48.marvelheroes.source.cache.series.SeriesCache

class SeriesRepository(
    private val cache: SeriesCache,
    private val source: SeriesSource,
    private val mapper: SeriesMapper
) : PaginatedRepository<SeriesDto, GetSeriesParam, Series>(cache) {

    override fun dtoToModel(dto: List<SeriesDto>) = mapper.map(dto)

    override suspend fun apiCall(param: GetSeriesParam) =
        source.get(param.characterId, cache.limit(), cache.size())
}
