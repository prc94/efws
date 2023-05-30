package com.github.prc94.efws.data.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.bouncycastle.openpgp.PGPPublicKey
import org.bouncycastle.openpgp.PGPPublicKeyRing
import org.bouncycastle.openpgp.PGPUtil
import org.bouncycastle.openpgp.operator.bc.BcKeyFingerprintCalculator

@Converter(autoApply = true)
class PGPPublicKeyAttributeConverter : AttributeConverter<PGPPublicKey, ByteArray> {
    override fun convertToDatabaseColumn(attribute: PGPPublicKey): ByteArray =
        attribute.encoded

    override fun convertToEntityAttribute(dbData: ByteArray): PGPPublicKey =
        PGPUtil.getDecoderStream(dbData.inputStream())
            .use { PGPPublicKeyRing(it, BcKeyFingerprintCalculator()) }
            .publicKey
}