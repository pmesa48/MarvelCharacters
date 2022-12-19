package com.pmesa48.marvelheroes.source.api.common

open class ServiceException(
    open val code: Int,
    open val customMessage: String?
) : Exception(customMessage)

class GetHeroesListException(
    override val message: String?,
    override val code: Int,
    override val customMessage: String?
) : ServiceException(code, customMessage)