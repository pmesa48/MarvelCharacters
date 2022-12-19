package com.pmesa48.marvelheroes.source.api.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pmesa48.marvelheroes.source.api.dto.ServerErrorBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseService {

    protected suspend fun <T : Any> safeCall(call: suspend () -> Response<T>): ServerResponse<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            return parseException(e)
        }

        return if (!response.isSuccessful) {
            val errorBody = response.errorBody()
            val gson = Gson()
            val serverError = gson.fromJson(errorBody.toString(), ServerErrorBody::class.java)
            ServerResponse.Error("${serverError.code} -> ${serverError.message}")
        } else {
            return if (response.body() == null) {
                ServerResponse.Error("Unknown server error")
            } else {
                ServerResponse.Success(response.body()!!)
            }
        }
    }

    private fun <T : Any> parseException(e: Exception): ServerResponse<T> = when(e) {
        is SocketTimeoutException -> ServerResponse.Error("Service not available at the moment, try later")
        is HttpException -> ServerResponse.Error(e.message())
        is IOException -> ServerResponse.Error("Network connection problem")
        else -> ServerResponse.Error("Unknown error")
    }
}