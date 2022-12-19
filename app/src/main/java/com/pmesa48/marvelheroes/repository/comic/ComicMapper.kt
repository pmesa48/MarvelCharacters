package com.pmesa48.marvelheroes.repository.comic

import com.pmesa48.marvelheroes.source.api.dto.ComicDto
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.model.Comic
import com.pmesa48.marvelheroes.repository.common.ListMapper
import com.pmesa48.marvelheroes.repository.common.ListMapper.Companion.QUALITY

class ComicMapper: ListMapper<ComicDto, Comic> {

    override fun map(data: List<ComicDto>) =
        data.filter { it.title != null && it.id != null }
            .map {
                Comic(
                    id = it.id!!,
                    name = it.title!!,
                    image = "${it.thumbnail?.path}$QUALITY.${it.thumbnail?.extension}",
                )
            }
}
