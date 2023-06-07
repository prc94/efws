package com.github.prc94.efws.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.net.URL

@Entity
class Storage(
    var name: String,
    var url: URL,
    var bucketName: String,
    var accessKey: String,
    var secretKey: String,
    @Id @GeneratedValue var id: Int? = null
)