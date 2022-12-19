package com.pmesa48.marvelheroes.repository.events

import com.pmesa48.marvelheroes.repository.common.PaginatedParam

class GetEventsParam(characterId: String, lastVisibleItem: Int)
    : PaginatedParam(characterId, lastVisibleItem)
