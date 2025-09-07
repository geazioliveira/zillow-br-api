package com.zillowbrapi.auth.profile.dtos

import com.zillowbrapi.auth.profile.errors.ProfileErrorMessage
import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class ProfileUpdateRequest (
    @field:NotNull(message = ProfileErrorMessage.USER_ID_REQUIRED)
    @field:NotBlank(message = ProfileErrorMessage.USER_ID_NOT_BLANK)
    var user: String,

    @field:NotNull(message = ProfileErrorMessage.TYPE_REQUIRED)
    var type: ProfileType,

    @field:URL(message = ProfileErrorMessage.AVATAR_URL_INVALID_FORMAT)
    @field:Size(max = 500, message = ProfileErrorMessage.AVATAR_URL_INVALID_LENGTH)
    var avatarUrl: String? = null,

    @field:URL(message = ProfileErrorMessage.COVER_URL_INVALID_FORMAT)
    @field:Size(max = 500, message = ProfileErrorMessage.COVER_URL_INVALID_LENGTH)
    var coverUrl: String? = null,

    @field:Size(max = 1000, message = ProfileErrorMessage.BIO_SIZE_INVALID)
    var bio: String? = null,

    @field:Size(max = 100, message = ProfileErrorMessage.PRIMARY_CITY_SIZE_INVALID)
    @field:Pattern(
        regexp = "^[\\p{L}\\s\\-']+$",
        message = ProfileErrorMessage.PRIMARY_CITY_INVALID
    )
    var primaryCity: String? = null,

    @field:Size(max = 2, message = ProfileErrorMessage.PRIMARY_STATE_SIZE_INVALID)
    @field:Pattern(
        regexp = "^[A-Z]{2}$",
        message = ProfileErrorMessage.PRIMARY_STATE_INVALID
    )
    var primaryState: String? = null,

    @field:NotNull(message = ProfileErrorMessage.VISIBILITY_NOT_NULL)
    var visibility: Visibility = Visibility.PRIVATE
)