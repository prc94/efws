package com.github.prc94.efws.data.dto

data class FileDto(
    val id: Long,
    val name: String,
    val path: String,
    val storage: StorageDto
)
