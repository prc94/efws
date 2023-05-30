package com.github.prc94.efws.data.mapper

import com.github.prc94.efws.data.dto.FileDto
import com.github.prc94.efws.data.entity.FileEntity
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface FileMapper {
    fun toDto(entity: FileEntity): FileDto
    fun toEntity(dto: FileDto): FileEntity
}