package com.pmesa48.marvelheroes.di

import com.pmesa48.marvelheroes.source.api.characters.CharacterSource
import com.pmesa48.marvelheroes.source.api.characters.CharacterSourceImpl
import com.pmesa48.marvelheroes.source.api.characters.CharactersApi
import com.pmesa48.marvelheroes.source.api.comics.ComicApi
import com.pmesa48.marvelheroes.source.api.comics.ComicSource
import com.pmesa48.marvelheroes.source.api.comics.ComicSourceImpl
import com.pmesa48.marvelheroes.source.api.series.SeriesApi
import com.pmesa48.marvelheroes.source.api.series.SeriesSource
import com.pmesa48.marvelheroes.source.api.series.SeriesSourceImpl
import com.pmesa48.marvelheroes.source.api.events.EventsApi
import com.pmesa48.marvelheroes.source.api.events.EventsSource
import com.pmesa48.marvelheroes.source.api.events.EventsSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SourceModule {

    @Provides
    fun providesCharactersApi(retrofit: Retrofit) : CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    @Provides
    fun providesComicApi(retrofit: Retrofit) : ComicApi {
        return retrofit.create(ComicApi::class.java)
    }

    @Provides
    fun providesSeriesApi(retrofit: Retrofit) : SeriesApi {
        return retrofit.create(SeriesApi::class.java)
    }

    @Provides
    fun providesEventsApi(retrofit: Retrofit) : EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    @Provides
    fun providesCharacterSource(api: CharactersApi): CharacterSource {
        return CharacterSourceImpl(api)
    }

    @Provides
    fun providesComicSource(api: ComicApi): ComicSource {
        return ComicSourceImpl(api)
    }

    @Provides
    fun providesSeriesSource(api: SeriesApi): SeriesSource {
        return SeriesSourceImpl(api)
    }

    @Provides
    fun providesEventsSource(api: EventsApi): EventsSource {
        return EventsSourceImpl(api)
    }
}