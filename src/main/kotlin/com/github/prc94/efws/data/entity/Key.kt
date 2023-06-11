package com.github.prc94.efws.data.entity

import com.github.prc94.efws.data.converter.PGPPublicKeyAttributeConverter
import jakarta.persistence.*
import org.bouncycastle.openpgp.PGPPublicKey

@Entity
@Table(name = "ENCRYPTION_KEY")
class Key(
    val name: String,
    @Convert(converter = PGPPublicKeyAttributeConverter::class)
    val publicKey: PGPPublicKey,
    @Id @GeneratedValue val id: Int? = null
)