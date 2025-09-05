package com.zillowbrapi.auth.login.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest (
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Email must be a valid email address")
    @field:Size(max = 100, message = "Email must not exceed 100 characters")
    val email: String? = null,

    @field:NotBlank(message = "Password must not be blank")
    val password: String? = null
)