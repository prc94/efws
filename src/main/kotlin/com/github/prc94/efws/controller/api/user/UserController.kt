package com.github.prc94.efws.controller.api.user

import com.github.prc94.efws.data.dto.UserCreationDto
import com.github.prc94.efws.data.dto.UserDto
import com.github.prc94.efws.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/user")
class UserController(val service: UserService) {
    @PostMapping
    fun createUser(username: String, @RequestBody body: UserCreationDto): ResponseEntity<UserDto> =
        service.addUser(body)
            .let {
                ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/${it.id}")
                    .build().toUri()
                ).body(it)
            }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDto>> =
        ResponseEntity.ok(service.findAll())

    @PreAuthorize("hasPermission(#id, 'user', 'read')")
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<UserDto> =
        ResponseEntity.of(service.findById(id))

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody userDto: UserDto): ResponseEntity<UserDto> =
        ResponseEntity.ok(service.updateUser(id, userDto))

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): Unit =
        service.deleteUser(id)

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/self")
    fun getSelf(authentication: Authentication): ResponseEntity<UserDto> =
        ResponseEntity.of(service.findUser(authentication.name))
}