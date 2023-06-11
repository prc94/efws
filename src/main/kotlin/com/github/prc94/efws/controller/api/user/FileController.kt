package com.github.prc94.efws.controller.api.user

import com.github.prc94.efws.data.dto.FileCreationDto
import com.github.prc94.efws.data.dto.FileDto
import com.github.prc94.efws.service.UserService
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
class FileController(val service: UserService) {

    @PreAuthorize("hasPermission(#userId, 'user_files', 'create')")
    @PostMapping
    fun createFile(@PathVariable userId: Int, @RequestBody body: FileCreationDto): ResponseEntity<FileDto> =
        TODO("Implement createFile")

    @PreAuthorize("hasPermission(#userId, 'user_files', 'read')")
    @GetMapping
    fun getAllFiles(@PathVariable userId: Int): ResponseEntity<List<FileDto>> =
        ResponseEntity.of(service.findAllFiles(userId))

    @PreAuthorize("hasPermission(#userId, 'user_files', 'read')")
    @GetMapping("/{id}")
    fun getFile(@PathVariable userId: Int, @PathVariable id: Int): ResponseEntity<FileDto> =
        ResponseEntity.of(service.findFileById(userId, id))

    @PreAuthorize("hasPermission(#userId, 'user_files', 'delete')")
    @DeleteMapping("/{id}")
    fun deleteFile(@PathVariable userId: Int, @PathVariable id: Int): Unit =
        TODO("Implement delete file")
}