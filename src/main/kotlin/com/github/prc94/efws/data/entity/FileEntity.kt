package com.github.prc94.efws.data.entity

import jakarta.persistence.*

@Entity
class FileEntity(
    var name: String,
    @Column(unique = true) var path: String,
    @ManyToOne var storage: Storage,
    @ManyToOne var owner: User,
    @Id @GeneratedValue var id: Int? = null
)