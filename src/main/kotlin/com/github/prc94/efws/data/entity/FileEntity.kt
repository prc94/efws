package com.github.prc94.efws.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class FileEntity(
    var name: String,
    @Column(unique = true) var path: String,
    @ManyToOne var storage: Storage,
    @Id @GeneratedValue var id: Int? = null
)