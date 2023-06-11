package com.github.prc94.efws.service

import com.github.prc94.efws.data.dto.StorageCreationDto
import com.github.prc94.efws.data.dto.StorageDto
import com.github.prc94.efws.data.entity.Storage
import com.github.prc94.efws.data.mapper.StorageMapper
import com.github.prc94.efws.data.repository.FileRepository
import com.github.prc94.efws.data.repository.StorageRepository
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.http.Method
import org.springframework.stereotype.Service
import java.net.URL
import java.util.Optional
import java.util.concurrent.TimeUnit

@Service
class StorageService(val storageRepository: StorageRepository,
                     val fileRepository: FileRepository,
                     val mapper: StorageMapper) {

    private val minioClients: MutableMap<Storage, MinioClient> = mutableMapOf()

    private val Storage.client
        get() = minioClients.computeIfAbsent(this) {
            MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build()
        }

    fun createStorage(dto: StorageCreationDto): StorageDto =
        dto.let { (name, url, bucketName, accessKey, secretKey) ->
            Storage(name, URL(url), bucketName, accessKey, secretKey)
                .let(storageRepository::save)
                .let(mapper::toDto)
        }

    fun findStorage(name: String): Optional<StorageDto> =
        storageRepository.findByName(name)
            .map(mapper::toDto)

    fun findStorage(id: Int): Optional<StorageDto> =
        storageRepository.findById(id)
            .map(mapper::toDto)

    fun findAll(): List<StorageDto> =
        storageRepository.findAll()
            .map(mapper::toDto)

    fun deleteStorage(id: Int) {
        storageRepository.deleteById(id)
    }

    //TODO implement proper exceptions for orElseThrow
    fun getPresignedUrl(storageId: Int, fileId: Int, method: Method): String =
        storageRepository.findById(storageId)
            .orElseThrow()
            .let {
                it.client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                        .bucket(it.bucketName)
                        .`object`(fileRepository.findById(fileId).orElseThrow().path)
                        .method(method)
                        .expiry(4, TimeUnit.HOURS)
                        .build()
                )
            }
}