package com.pmesa48.marvelheroes.repository.common

import com.pmesa48.marvelheroes.model.Resource
import com.pmesa48.marvelheroes.source.api.common.ServerResponse
import com.pmesa48.marvelheroes.source.api.common.ServerResponse.*
import com.pmesa48.marvelheroes.source.api.dto.ServiceWrapper
import com.pmesa48.marvelheroes.source.cache.common.PaginatedCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class PaginatedRepository<D: Any, P: Param, M: Any> (
    private val cache: PaginatedCache<M, P>
){
    suspend fun load(param: P): Flow<Resource<List<M>>> = flow {
        if(cache.needNextPage(param)) {
            when(val response = apiCall(param)) {
                is Success -> {
                    results(response)?.let {
                        cache.load(dtoToModel(it), total(response))
                        emit(Resource.Found(cache.all())) } ?: emit(Resource.Error())
                }
                is Error -> emit(Resource.Error(response.message))
            }
        }
    }

    private fun results(response: Success<ServiceWrapper<List<D>>>) =
        response.data.data?.results

    private fun total(response: Success<ServiceWrapper<List<D>>>) =
        response.data.data?.total ?: 0

    abstract fun dtoToModel(dto: List<D>): List<M>

    abstract suspend fun apiCall(param: P) : ServerResponse<ServiceWrapper<List<D>>>
}