package com.zillowbrapi.auth.user.types

import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.model.UserEntity

sealed class UserRequestType {
    data class Create(val request: UserCreateRequest) : UserRequestType()
    data class Update(val request: UserUpdateRequest, val existingUser: UserEntity) : UserRequestType()
}