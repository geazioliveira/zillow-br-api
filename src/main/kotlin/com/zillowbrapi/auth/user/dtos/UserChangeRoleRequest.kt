package com.zillowbrapi.auth.user.dtos

import com.zillowbrapi.auth.user.types.UserChangeRoleType
import com.zillowbrapi.auth.user.types.UserRole
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

data class UserChangeRoleRequest (
    @field:NotNull(message = "Roles cannot be null")
    @Valid
    val role: UserRole? = UserRole.CONSUMER,

    @field:NotNull(message = "Type cannot be null")
    val type: UserChangeRoleType? = UserChangeRoleType.ADD
)