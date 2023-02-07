package com.site.infrastructure.services.hashers

import java.security.MessageDigest

class Sha256Hasher : IHasher {
    override fun hash(value: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(value.toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}