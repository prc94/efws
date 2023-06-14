package com.github.prc94.efws.service

import com.github.prc94.efws.data.dto.*
import com.github.prc94.efws.data.entity.User
import com.github.prc94.efws.data.mapper.KeyMapper
import com.github.prc94.efws.data.mapper.UserMapper
import com.github.prc94.efws.data.repository.UserRepository
import com.github.prc94.efws.exception.EntityCreationException
import com.github.prc94.efws.exception.EntityNotFoundException
import com.github.prc94.efws.exception.UsernameNotAvailableException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional


@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val keyMapper: KeyMapper
) : UserDetailsService {
    init {
        if (!userRepository.existsByUsername("admin"))
            User("admin", "{noop}admin", isAdmin = true, isEnabled = true)
                .let(userRepository::save)
    }

    fun findAll(): List<UserDto> =
        userRepository.findAll()
            .map(userMapper::toDto)

    fun addUser(userCreationDto: UserCreationDto): UserDto =
        userCreationDto.let { (username, password, isAdmin) ->
            if (!userRepository.existsByUsername(username))
                User(username, passwordEncoder.encode(password), isAdmin, true)
                    .let(userRepository::save)
                    .let(userMapper::toDto)
            else
                throw EntityCreationException("User with username $username already exists")
        }

    fun updateUser(id: Int, userDto: UserDto): UserDto =
        userRepository.findById(id)
            .orElseThrow { EntityNotFoundException("User with id $id") }
            .let { user ->
                if (userDto.username != user.username && userRepository.existsByUsername(userDto.username))
                    throw UsernameNotAvailableException(userDto.username)

                user.apply {
                    username = userDto.username
                    isAdmin = userDto.isAdmin
                    isEnabled = userDto.isEnabled
                }

                userMapper.toDto(user)
            }

    fun deleteUser(id: Int): Unit =
        userRepository.deleteById(id)

    fun findUser(username: String): Optional<UserDto> =
        userRepository.findByUsername(username)
            .map(userMapper::toDto)

    fun findById(id: Int): Optional<UserDto> =
        userRepository.findById(id)
            .map(userMapper::toDto)

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User $username not found!") }
            .userDetails

    fun findKeyById(userId: Int, keyId: Int): Optional<KeyDto> =
        userRepository.findById(userId)
            .flatMap { user -> Optional.ofNullable(user.keys.find { it.id == keyId }) }
            .map(keyMapper::toDto)

    fun findAllKeys(userId: Int): Optional<List<KeyDto>> =
        userRepository.findById(userId)
            .map { it.keys.map(keyMapper::toDto) }

    fun createKey(userId: Int, keyCreationDto: KeyCreationDto): KeyDto =
        userRepository.findById(userId)
            .orElseThrow { TODO("Create a proper exception type") }
            .let { user ->
                keyMapper.toEntity(keyCreationDto)
                    .also {
                        user.keys.add(it)
                        userRepository.save(user)
                    }
            }
            .let(keyMapper::toDto)

    fun deleteKey(userId: Int, keyId: Int): Unit =
        userRepository.findById(userId)
            .ifPresent { user ->
                user.keys.removeIf { it.id == keyId }
            }

}