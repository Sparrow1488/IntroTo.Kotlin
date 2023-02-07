package com.site.infrastructure.services.hashers

interface IHasher {
    fun hash(value: String) : String
}