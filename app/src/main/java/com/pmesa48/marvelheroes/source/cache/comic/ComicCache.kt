package com.pmesa48.marvelheroes.source.cache.comic

import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.repository.comic.GetComicsParam
import com.pmesa48.marvelheroes.source.cache.common.PaginatedCache

interface ComicCache : PaginatedCache<Comic, GetComicsParam>