package com.pmesa48.marvelheroes.source.api.common

import com.pmesa48.marvelheroes.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val timeStamp = System.currentTimeMillis().toString()
        val url = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_PARAM, BuildConfig.MARVEL_API_PUBLIC_KEY)
            .addQueryParameter(HASH, md5("${timeStamp}${BuildConfig.MARVEL_API_PRIVATE_KEY}${BuildConfig.MARVEL_API_PUBLIC_KEY}"))
            .addQueryParameter(TIME_STAMP, timeStamp)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        private const val API_KEY_PARAM = "apikey"
        private const val TIME_STAMP = "ts"
        private const val HASH = "hash"
    }

}
