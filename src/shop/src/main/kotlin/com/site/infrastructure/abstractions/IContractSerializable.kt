package com.site.infrastructure.abstractions

interface IContractSerializable<TResponse> {
    fun toSerializable() : TResponse
}