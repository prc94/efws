package com.github.prc94.efws.data.converter

import org.bouncycastle.bcpg.ArmoredOutputStream
import org.bouncycastle.openpgp.PGPPublicKey
import org.bouncycastle.openpgp.PGPPublicKeyRing
import org.bouncycastle.openpgp.PGPUtil
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import java.io.ByteArrayOutputStream

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
class PGPPublicKeyConverter {
    fun keyToString(key: PGPPublicKey): String =
        ByteArrayOutputStream()
            .also {
                ArmoredOutputStream(it)
                    .use(key::encode)
            }
            .toString()

    fun stringToKey(string: String): PGPPublicKey =
        PGPUtil.getDecoderStream(string.byteInputStream())
            .use { PGPPublicKeyRing(it, BcKeyFingerprintCalculator()) }
            .publicKey
}