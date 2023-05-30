package com.github.prc94.efws.data.mapper

import com.github.prc94.efws.data.dto.StorageDto
import com.github.prc94.efws.data.entity.Storage
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface StorageMapper {
    fun toDto(entity: Storage): StorageDto
    fun toEntity(dto: StorageDto): Storage
}