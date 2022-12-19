package com.pmesa48.marvelheroes.source.cache.event

import com.pmesa48.marvelheroes.source.cache.common.InMemoryPaginatedCache
import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.repository.events.GetEventsParam
import javax.inject.Inject

class EventsCacheImpl @Inject constructor()
    : EventsCache, InMemoryPaginatedCache<Event, GetEventsParam>() {
    override fun threshold() = THRESHOLD

    override fun limit() = LIMIT

    companion object {
        private const val LIMIT = 20
        private const val THRESHOLD = 5
    }
}