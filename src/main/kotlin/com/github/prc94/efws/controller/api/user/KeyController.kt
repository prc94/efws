package com.github.prc94.efws.controller.api.user

import com.github.prc94.efws.data.dto.KeyCreationDto
import com.github.prc94.efws.data.dto.KeyDto
import com.github.prc94.efws.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api/user/{userId}/key")
class KeyController(val service: UserService) {

    @PreAuthorize("hasPermission(#userId, 'user_keys', 'create')")
    @PostMapping
    fun createKey(@PathVariable userId: Int, @RequestBody body: KeyCreationDto): ResponseEntity<KeyDto> =
        service.createKey(userId, body)
            .let {
                ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/${it.id}")
                        .build().toUri()
                ).body(it)
            }

    @PreAuthorize("hasPermission(#userId, 'user_keys', 'update')")
    @PutMapping("/{id}")
    fun updateKey(
        @PathVariable userId: Int,
        @PathVariable id: Int,
        @RequestBody keyDto: KeyDto
    ): ResponseEntity<KeyDto> =
        TODO("Implement updateKey")

    @PreAuthorize("hasPermission(#userId, 'user_keys', 'delete')")
    @DeleteMapping("/{id}")
    fun deleteKey(@PathVariable userId: Int, @PathVariable id: Int): Unit =
        service.deleteKey(userId, id)

    @PreAuthorize("hasPermission(#userId, 'user_keys', 'read')")
    @GetMapping
    fun getAllKeys(@PathVariable userId: Int): ResponseEntity<List<KeyDto>> =
        ResponseEntity.of(service.findAllKeys(userId))

    @PreAuthorize("hasPermission(#userId, 'user_keys', 'read')")
    @GetMapping("/{id}")
    fun getKey(@PathVariable userId: Int, @PathVariable id: Int): ResponseEntity<KeyDto> =
        ResponseEntity.of(service.findKeyById(userId, id))
}