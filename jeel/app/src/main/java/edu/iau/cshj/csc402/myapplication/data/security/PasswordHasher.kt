package edu.iau.cshj.csc402.myapplication.data.security

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64

object PasswordHasher {

    private const val ITERATIONS = 120_000
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16

    fun generateSalt(): String {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }

    fun hashPassword(password: String, salt: String): String {
        val saltBytes = Base64.decode(salt, Base64.NO_WRAP)

        val spec = PBEKeySpec(
            password.toCharArray(),
            saltBytes,
            ITERATIONS,
            KEY_LENGTH
        )

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")

        return Base64.encodeToString(
            factory.generateSecret(spec).encoded,
            Base64.NO_WRAP
        ).also {
            spec.clearPassword()
        }
    }

    fun verifyPassword(
        password: String,
        salt: String,
        expectedHash: String
    ): Boolean {
        return hashPassword(password, salt) == expectedHash
    }
}
