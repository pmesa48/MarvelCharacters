package com.pmesa48.marvelheroes.source.cache.comic

import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.repository.comic.GetComicsParam
import com.pmesa48.marvelheroes.source.cache.common.InMemoryPaginatedCache
import javax.inject.Inject

class ComicCacheImpl @Inject constructor() :
    ComicCache, InMemoryPaginatedCache<Comic, GetComicsParam>() {
    override fun threshold() = THRESHOLD

    override fun limit() = LIMIT

    companion object {
        private const val LIMIT = 20
        private const val THRESHOLD = 5
    }
}