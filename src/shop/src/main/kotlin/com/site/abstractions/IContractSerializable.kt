package com.site.abstractions

interface IContractSerializable<TResponse> {
    fun toSerializable() : TResponse
}