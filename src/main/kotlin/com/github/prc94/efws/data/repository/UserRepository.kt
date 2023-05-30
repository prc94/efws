package com.github.prc94.efws.data.repository

import com.github.prc94.efws.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
    fun existsByUsername(username: String): Boolean
}