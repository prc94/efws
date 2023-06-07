package com.github.prc94.efws.data.repository

import com.github.prc94.efws.data.entity.Storage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StorageRepository : JpaRepository<Storage, Int> {

    fun findByName(name: String): Optional<Storage>
}