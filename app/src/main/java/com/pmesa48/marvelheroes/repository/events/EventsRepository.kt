package com.pmesa48.marvelheroes.repository.events

import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.repository.common.PaginatedRepository
import com.pmesa48.marvelheroes.source.api.dto.EventDto
import com.pmesa48.marvelheroes.source.api.events.EventsSource
import com.pmesa48.marvelheroes.source.cache.event.EventsCache

class EventsRepository(
    private val cache: EventsCache,
    private val source: EventsSource,
    private val mapper: EventsMapper
) : PaginatedRepository<EventDto, GetEventsParam, Event>(cache) {

    override fun dtoToModel(dto: List<EventDto>) = mapper.map(dto)

    override suspend fun apiCall(param: GetEventsParam) =
        source.get(param.filterId, cache.limit(), cache.size())
}
