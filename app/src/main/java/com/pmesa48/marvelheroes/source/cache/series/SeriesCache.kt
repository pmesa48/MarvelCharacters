package com.pmesa48.marvelheroes.source.cache.series

import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.repository.series.GetSeriesParam
import com.pmesa48.marvelheroes.source.cache.common.PaginatedCache

interface SeriesCache : PaginatedCache<Series, GetSeriesParam>
