package com.github.prc94.efws.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.net.URL

@Entity
class Storage(
    var name: String,
    var url: URL,
    @Id @GeneratedValue var id: Int? = null
)