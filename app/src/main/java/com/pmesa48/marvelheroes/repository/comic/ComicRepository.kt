package com.pmesa48.marvelheroes.repository.comic

import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.source.api.comics.ComicSource
import com.pmesa48.marvelheroes.source.api.dto.ComicDto
import com.pmesa48.marvelheroes.repository.common.PaginatedRepository
import com.pmesa48.marvelheroes.source.cache.comic.ComicCacheImpl

class ComicRepository(
    private val comicCache: ComicCacheImpl,
    private val comicSource: ComicSource,
    private val comicMapper: ComicMapper
) : PaginatedRepository<ComicDto, GetComicsParam, Comic>(comicCache) {

    override fun dtoToModel(dto: List<ComicDto>) = comicMapper.map(dto)

    override suspend fun apiCall(param: GetComicsParam) =
        comicSource.get(param.characterId, comicCache.limit(), comicCache.size())
}