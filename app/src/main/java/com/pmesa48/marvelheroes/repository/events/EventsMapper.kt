package com.pmesa48.marvelheroes.repository.events

import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.repository.common.ListMapper
import com.pmesa48.marvelheroes.source.api.dto.EventDto

class EventsMapper : ListMapper<EventDto, Event> {

    private fun map(data: EventDto) = Event(
        id = data.id!!,
        name = data.title!!,
        image = "${data.thumbnail?.path}${ListMapper.QUALITY}.${data.thumbnail?.extension}",
    )

    override fun map(data: List<EventDto>) =
        data.filter { it.id != null && it.title != null }.map { map(it) }

}

