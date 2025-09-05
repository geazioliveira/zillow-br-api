package com.zillowbrapi.auth.user.dtos


import com.zillowbrapi.auth.user.errors.UserErrorMessages
import com.zillowbrapi.auth.user.model.User
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.Instant

data class UserCreateRequest (
    override val id: Long? = null,

    @field:NotBlank(message = UserErrorMessages.FIRST_NAME_BLANK)
    @field:Size(min = 2, max = 100, message = "First name must be between 1 and 100 characters")
    override val firstName: String? = null,

    @field:NotBlank(message = UserErrorMessages.LAST_NAME_BLANK)
    @field:Size(min = 2, max = 100, message = "Last name must be between 1 and 100 characters")
    override val lastName: String? = null,

    @field:NotBlank(message = UserErrorMessages.SCREEN_NAME_BLANK)
    @field:Size(min = 2, max = 100, message = "Screen name must be between 1 and 100 characters")
    override val screenName: String? = null,

    @field:Pattern(
        regexp = "^https?://.*\\.(jpg|jpeg|png|gif)$",
        message = "Photo URL must be a valid HTTP/HTTPS URL ending with jpg, jpeg, png, or gif"
    )
    override val photoUrl: String? = null,

    @field:NotBlank(message = UserErrorMessages.PASSWORD_BLANK)
    @field:Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=?])(?=\\S+$).{8,}$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and no whitespace"
    )
    override val password: String? = null,

    @field:NotBlank(message = UserErrorMessages.EMAIL_BLANK)
    @field:Email(message = UserErrorMessages.EMAIL_INVALID_FORMAT)
    @field:Size(max = 100, message = "Email must not exceed 100 characters")
    override val email: String? = null,

    override val createdAt: Instant? = null,
    override val updatedAt: Instant? = null,
    override val deletedAt: Instant? = null
): User
