package com.github.prc94.efws.data.mapper

import com.github.prc94.efws.data.converter.PGPPublicKeyConverter
import com.github.prc94.efws.data.dto.KeyCreationDto
import com.github.prc94.efws.data.dto.KeyDto
import com.github.prc94.efws.data.entity.Key
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = [PGPPublicKeyConverter::class])
interface KeyMapper {
    fun toDto(entity: Key): KeyDto
    fun toEntity(dto: KeyDto): Key
    fun toEntity(creationDto: KeyCreationDto): Key
}