package com.github.prc94.efws.data.entity

import jakarta.persistence.*
import org.bouncycastle.openpgp.PGPPublicKey

@Entity
@Table(name = "keys")
class Key(
    val name: String,
    @Convert val publicKey: PGPPublicKey,
    @Id @GeneratedValue val id: Int? = null
)