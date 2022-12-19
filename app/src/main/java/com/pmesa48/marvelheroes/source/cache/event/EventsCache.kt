package com.pmesa48.marvelheroes.source.cache.event

import com.pmesa48.marvelheroes.source.cache.common.PaginatedCache
import com.pmesa48.marvelheroes.model.Event
import com.pmesa48.marvelheroes.repository.events.GetEventsParam

interface EventsCache : PaginatedCache<Event, GetEventsParam>
