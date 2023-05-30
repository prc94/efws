package com.github.prc94.efws.data.mapper

import com.github.prc94.efws.data.dto.UserDto
import com.github.prc94.efws.data.entity.User
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UserMapper {
    fun toDto(entity: User): UserDto
    fun toEntity(dto: UserDto): User
}