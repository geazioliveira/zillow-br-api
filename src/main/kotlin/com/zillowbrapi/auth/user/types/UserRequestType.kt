package com.zillowbrapi.auth.user.types

import com.zillowbrapi.auth.user.UserEntity
import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest

sealed class UserRequestType {
    data class Create(val request: UserCreateRequest) : UserRequestType()
    data class Update(val request: UserUpdateRequest, val existingUser: UserEntity) : UserRequestType()
}