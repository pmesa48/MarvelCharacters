package com.pmesa48.marvelheroes.repository.series

import com.pmesa48.marvelheroes.repository.common.ListMapper
import com.pmesa48.marvelheroes.model.Series
import com.pmesa48.marvelheroes.source.api.dto.SeriesDto

class SeriesMapper : ListMapper<SeriesDto, Series> {

    private fun map(data: SeriesDto) = Series(
        id = data.id!!,
        title = data.title!!,
        image = "${data.thumbnail?.path}${ListMapper.QUALITY}.${data.thumbnail?.extension}",
    )

    override fun map(data: List<SeriesDto>) =
        data.filter { it.id != null && it.title != null }.map { map(it) }
}
