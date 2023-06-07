package com.github.prc94.efws.service

import com.github.prc94.efws.data.dto.StorageCreationDto
import com.github.prc94.efws.data.dto.StorageDto
import com.github.prc94.efws.data.entity.Storage
import com.github.prc94.efws.data.mapper.StorageMapper
import com.github.prc94.efws.data.repository.StorageRepository
import io.minio.MinioClient
import org.springframework.stereotype.Service
import java.net.URL
import java.util.Optional

@Service
class StorageService(val repository: StorageRepository, val mapper: StorageMapper) {

    private val minioClients: MutableMap<Storage, MinioClient> = mutableMapOf()

    private val Storage.client
        get() = minioClients.computeIfAbsent(this) {
            MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build()
        }

    fun createStorage(dto: StorageCreationDto): StorageDto =
        dto.let { (name, url, accessKey, secretKey) ->
            Storage(name, URL(url), accessKey, secretKey)
                .let(repository::save)
                .let(mapper::toDto)
        }

    fun findStorage(name: String): Optional<StorageDto> =
        repository.findByName(name)
            .map(mapper::toDto)

    fun findAll(): List<StorageDto> =
        repository.findAll()
            .map(mapper::toDto)
}