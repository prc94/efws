package com.github.prc94.efws.controller.api

import com.github.prc94.efws.data.dto.StorageCreationDto
import com.github.prc94.efws.data.dto.StorageDto
import com.github.prc94.efws.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URL

@RestController
@RequestMapping("/api/storage")
class StorageController(val service: StorageService) {

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun createStorage(name: String, @RequestBody body: StorageCreationDto): ResponseEntity<StorageDto> =
        service.createStorage(body)
            .let {
                ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/${it.id}")
                        .build().toUri())
                    .body(it)
            }

    /*@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun updateStorage(@PathVariable id: Int, @RequestBody storage: StorageDto): ResponseEntity<StorageDto> =
        ResponseEntity.ok(service.updateStorage(id, storage))*/

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteStorage(@PathVariable id: Int): Unit =
        service.deleteStorage(id)

    @GetMapping("/{id}")
    fun getStorage(@PathVariable id: Int): ResponseEntity<StorageDto> =
        ResponseEntity.of(service.findStorage(id))
    
    @GetMapping
    fun getAll(): ResponseEntity<List<StorageDto>> =
        ResponseEntity.ok(service.findAll())
}