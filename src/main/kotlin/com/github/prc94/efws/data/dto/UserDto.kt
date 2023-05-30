package com.github.prc94.efws.data.dto

data class UserDto(
    val id: Int,
    val username: String,
    val isAdmin: Boolean,
    val isEnabled: Boolean
)
