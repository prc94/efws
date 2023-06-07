package com.github.prc94.efws.data.repository

import com.github.prc94.efws.data.entity.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface FileRepository : JpaRepository<FileEntity, Int> {
    fun findByName(name: String): Optional<FileEntity>
}