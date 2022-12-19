package com.pmesa48.marvelheroes.di

import com.pmesa48.marvelheroes.source.api.common.AuthInterceptor
import com.pmesa48.marvelheroes.source.cache.character.CharacterCache
import com.pmesa48.marvelheroes.source.cache.character.CharacterCacheImpl
import com.pmesa48.marvelheroes.source.cache.comic.ComicCache
import com.pmesa48.marvelheroes.source.cache.comic.ComicCacheImpl
import com.pmesa48.marvelheroes.source.cache.series.SeriesCache
import com.pmesa48.marvelheroes.source.cache.series.SeriesCacheImpl
import com.pmesa48.marvelheroes.source.cache.event.EventsCache
import com.pmesa48.marvelheroes.source.cache.event.EventsCacheImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesCharactersCache() : CharacterCache {
        return CharacterCacheImpl()
    }

    @Provides
    @Singleton
    fun providesComicsCache() : ComicCache {
        return ComicCacheImpl()
    }

    @Provides
    @Singleton
    fun providesSeriesCache() : SeriesCache {
        return SeriesCacheImpl()
    }

    @Provides
    @Singleton
    fun providesEventsCache() : EventsCache {
        return EventsCacheImpl()
    }

    @Provides
    fun providesHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun providesAuthInterceptor() : AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ) : Retrofit {
        return Retrofit.Builder().baseUrl("https://gateway.marvel.com:443/")
            .client(
                OkHttpClient()
                    .newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(authInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
            ).addConverterFactory(GsonConverterFactory.create()).build()
    }
}