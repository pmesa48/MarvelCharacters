package com.pmesa48.marvelheroes.source.cache.series

import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.repository.series.GetSeriesParam
import com.pmesa48.marvelheroes.source.cache.common.InMemoryPaginatedCache
import javax.inject.Inject

class SeriesCacheImpl @Inject constructor():
    SeriesCache, InMemoryPaginatedCache<Series, GetSeriesParam>() {

    override fun threshold() = THRESHOLD

    override fun limit() = LIMIT

    companion object {
        private const val LIMIT = 20
        private const val THRESHOLD = 5
    }

}