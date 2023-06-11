package com.github.prc94.efws.service

import com.github.prc94.efws.data.dto.FileDto
import com.github.prc94.efws.data.mapper.FileMapper
import com.github.prc94.efws.data.repository.FileRepository
import com.github.prc94.efws.data.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class FileService(val fileRepository: FileRepository,
                  val userRepository: UserRepository,
                  val mapper: FileMapper) {

    fun findFileById(fileId: Int): Optional<FileDto> =
        fileRepository.findById(fileId)
            .map(mapper::toDto)

    fun findAllFiles(userId: Int): Optional<List<FileDto>> =
        userRepository.findById(userId)
            .map { it.files.map(mapper::toDto) }
}