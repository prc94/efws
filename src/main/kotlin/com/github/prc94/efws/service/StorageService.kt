package com.github.prc94.efws.service

import io.minio.MinioClient
import org.springframework.stereotype.Service
import java.net.URL

data class Storage(val url: URL, val accessKey: String, val secretKey: String) {
    init {
        TODO("Placeholder storage")
    }
}

@Service
class StorageService {
    private val minioClients: MutableMap<Storage, MinioClient> = mutableMapOf()

    private val Storage.client
        get() = minioClients.computeIfAbsent(this) {
            MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build()
        }
}