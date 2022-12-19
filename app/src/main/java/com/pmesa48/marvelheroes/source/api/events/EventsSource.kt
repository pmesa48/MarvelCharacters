package com.pmesa48.marvelheroes.source.api.events

import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.source.api.dto.EventDto

interface EventsSource {

    suspend fun get(
        characterId: String,
        limit: Int,
        offset: Int
    ) : ServerResponse<ServiceWrapper<List<EventDto>>>
}
