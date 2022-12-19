package com.pmesa48.marvelheroes.repository.character

import com.pmesa48.marvelheroes.model.Character
import com.pmesa48.marvelheroes.source.api.dto.CharacterDto
import com.pmesa48.marvelheroes.repository.common.ListMapper
import com.pmesa48.marvelheroes.repository.common.ListMapper.Companion.QUALITY

class CharacterMapper: ListMapper<CharacterDto, Character> {

    private fun CharacterDto.toModel() = Character(
        id = this.id!!,
        name = this.name!!,
        description = this.description ?: "",
        thumbnail = "${this.thumbnail?.path}$QUALITY.${this.thumbnail?.extension}",
        background = "${this.thumbnail?.path}.${this.thumbnail?.extension}"
    )

    override fun map(data: List<CharacterDto>) =
        data.filter { dto -> dto.id != null && dto.name != null }
            .map { dto -> dto.toModel() }
}


