package com.github.prc94.efws.controller.api.user

import com.github.prc94.efws.data.dto.FileCreationDto
import com.github.prc94.efws.data.dto.FileDto
import com.github.prc94.efws.service.FileService
import com.github.prc94.efws.service.StorageService
import io.minio.http.Method
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user/{userId}/file")
class FileController(
    val fileService: FileService,
    val storageService: StorageService
) {

    @PreAuthorize("hasPermission(#userId, 'user_files', 'create')")
    @PostMapping
    fun createFile(@PathVariable userId: Int, @RequestBody body: FileCreationDto): ResponseEntity<FileDto> =
        TODO("Implement createFile")

    @PreAuthorize("hasPermission(#userId, 'user_files', 'read')")
    @GetMapping
    fun getAllFiles(@PathVariable userId: Int): ResponseEntity<List<FileDto>> =
        ResponseEntity.of(fileService.findAllFiles(userId))

    @PreAuthorize("hasPermission(#userId, 'user_files', 'read')")
    @GetMapping("/{id}")
    fun getFile(@PathVariable userId: Int, @PathVariable id: Int): ResponseEntity<FileDto> =
        ResponseEntity.of(fileService.findFileById(id))

    @PreAuthorize("hasPermission(#userId, 'user_files', 'delete')")
    @DeleteMapping("/{id}")
    fun deleteFile(@PathVariable userId: Int, @PathVariable id: Int): Unit =
        TODO("Implement delete file")

    //Modify permission evaluator accordingly
    @PreAuthorize("#hasPermission(#id, 'file', #method)")
    @GetMapping("/{id}/link")
    fun getLink(@PathVariable userId: Int, @PathVariable id: Int, method: String): ResponseEntity<String> =
        ResponseEntity.ok(storageService.getPresignedUrl(id, method))
}