package com.example.lifffter.core.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT


// encrypt, decrypt and genKey
class SecurityUtil {
    private val provider = "AndroidKeyStore"
    private val cipher by lazy {
        Cipher.getInstance("AES/GCM/NoPadding")
    }
    private val charset by lazy {
        charset("UTF-8")
    }
    private val keyStore by lazy {
        KeyStore.getInstance(provider).apply {
            load(null)
        }
    }
    private val keyGenerator by lazy {
        KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider)
    }

    fun encryptData(keyAlias: String, text: String): Pair<ByteArray, ByteArray> {
        val secretKey = generateSecretKey(keyAlias)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedData = cipher.doFinal(text.toByteArray(charset))
        val iv = cipher.iv
        return Pair(iv, encryptedData)
    }

    fun decryptData(keyAlias: String, iv: ByteArray, encryptedData: ByteArray): String {
        val secretKey = getSecretKey(keyAlias)
        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)
        return cipher.doFinal(encryptedData).toString(charset)
    }

    // key that we will use in encryption and decryption
    @Synchronized
    private fun generateSecretKey(keyAlias: String): SecretKey {
        // val keyEntry = keyStore.getEntry(keyAlias, null)
        // rather than checking by pulling the whole material just for null or not,

        // we can use containsAlias too
        if(!keyStore.containsAlias(keyAlias))
        {
            return keyGenerator.apply {
                    init(
                        KeyGenParameterSpec
                            .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                            .setBlockModes(BLOCK_MODE_GCM)
                            .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                            .build()
                    )
                }.generateKey()
        }
        else
        {
            return getSecretKey(keyAlias)
        }
    }
    private fun getSecretKey(keyAlias: String) = (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey
}