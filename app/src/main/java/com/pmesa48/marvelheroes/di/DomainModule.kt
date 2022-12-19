package com.pmesa48.marvelheroes.di

import com.pmesa48.marvelheroes.source.api.comics.ComicSource
import com.pmesa48.marvelheroes.source.api.characters.CharacterSource
import com.pmesa48.marvelheroes.source.cache.character.CharacterCache
import com.pmesa48.marvelheroes.ui.comics.*
import com.pmesa48.marvelheroes.repository.*
import com.pmesa48.marvelheroes.repository.character.CharacterRepository
import com.pmesa48.marvelheroes.repository.character.CharacterMapper
import com.pmesa48.marvelheroes.repository.comic.ComicRepository
import com.pmesa48.marvelheroes.repository.comic.ComicMapper
import com.pmesa48.marvelheroes.repository.series.SeriesMapper
import com.pmesa48.marvelheroes.repository.series.SeriesRepository
import com.pmesa48.marvelheroes.source.api.series.SeriesSource
import com.pmesa48.marvelheroes.source.cache.comic.ComicCacheImpl
import com.pmesa48.marvelheroes.source.cache.series.SeriesCache
import com.pmesa48.marvelheroes.source.cache.event.EventsCache
import com.pmesa48.marvelheroes.repository.events.EventsMapper
import com.pmesa48.marvelheroes.repository.events.EventsRepository
import com.pmesa48.marvelheroes.source.api.events.EventsSource
import com.pmesa48.marvelheroes.ui.series.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun providesCharacterMapper() : CharacterMapper {
        return CharacterMapper()
    }

    @Provides
    fun providesComicMapper(): ComicMapper {
        return ComicMapper()
    }

    @Provides
    fun providesSeriesMapper(): SeriesMapper {
        return SeriesMapper()
    }

    @Provides
    fun providesEventsMapper(): EventsMapper {
        return EventsMapper()
    }

    @Provides
    fun providesCharacterRepository(
        source: CharacterSource,
        cache: CharacterCache,
        mapper: CharacterMapper
    ) : CharacterRepository {
        return CharacterRepository(source, cache, mapper)
    }


    @Provides
    fun providesComicRepository(
        source: ComicSource,
        mapper: ComicMapper,
        comic: ComicCacheImpl
    ) : ComicRepository {
        return ComicRepository(comic, source, mapper)
    }

    @Provides
    fun providesSeriesRepository(
        source: SeriesSource,
        mapper: SeriesMapper,
        cache: SeriesCache
    ) : SeriesRepository {
        return SeriesRepository(cache, source, mapper)
    }

    @Provides
    fun providesEventsRepository(
        source: EventsSource,
        mapper: EventsMapper,
        cache: EventsCache
    ) : EventsRepository {
        return EventsRepository(cache, source, mapper)
    }
}