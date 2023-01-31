package com.example.contracts

import kotlinx.serialization.Serializable

@Serializable
data class ContentResponse(var text: String)

@Serializable
data class UserResponse(var id: Int, var name: String)

@Serializable
data class UserCreateRequest(var name: String)

@Serializable
data class UserEditRequest(var cityId: Int)
