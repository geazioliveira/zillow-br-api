package com.zillowbrapi.auth.user.dtos

import com.zillowbrapi.auth.user.models.User
import com.zillowbrapi.auth.user.types.UserRole
import com.zillowbrapi.auth.user.types.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.Instant
import java.util.*

data class UserUpdateRequest(
    override val id: UUID? = null,

    @field:Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    override val firstName: String? = null,

    @field:Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    override val lastName: String? = null,

    @field:Size(min = 1, max = 30, message = "Screen name must be between 1 and 30 characters")
    override val screenName: String? = null,

    @field:Pattern(
        regexp = "^https?://.*\\.(jpg|jpeg|png|gif)$",
        message = "Photo URL must be a valid HTTP/HTTPS URL ending with jpg, jpeg, png, or gif"
    )
    override val photoUrl: String? = null,

    @field:Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit"
    )
    override val password: String? = null,

    @field:Email(message = "Email must be a valid email address")
    @field:Size(max = 100, message = "Email must not exceed 100 characters")
    override val email: String? = null,
    override val phone: String? = null,
    override val isVerified: Boolean? = false,
    override val emailVerified: Boolean? = false,
    override val phoneVerified: Boolean? = false,
    override val status: UserStatus? = null,
    override val roles: MutableSet<UserRole>? = null,
    override val createdAt: Instant? = null,
    override val updatedAt: Instant? = null,
    override val deletedAt: Instant? = null
) : User