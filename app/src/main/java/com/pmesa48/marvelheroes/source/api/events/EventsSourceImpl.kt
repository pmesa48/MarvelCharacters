package com.pmesa48.marvelheroes.source.api.events

import com.pmesa48.marvelheroes.source.api.common.BaseService

class EventsSourceImpl(
    private val api: EventsApi
) : BaseService(), EventsSource {

    override suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ) = safeCall { api.get(characterId, limit, offset) }
}