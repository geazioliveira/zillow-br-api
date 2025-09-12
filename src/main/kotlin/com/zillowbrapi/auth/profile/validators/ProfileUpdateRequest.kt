package com.zillowbrapi.auth.profile.validators

import com.zillowbrapi.auth.profile.errors.ProfileErrorMessage
import com.zillowbrapi.auth.profile.models.Profile
import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import com.zillowbrapi.auth.user.models.UserEntity
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL
import java.time.Instant
import java.util.*

data class ProfileUpdateRequest (
    override val id: UUID? = null,
    override var user: UserEntity? = null,
    override var userId: UUID? = null,
    override var type: ProfileType,
    @field:URL(message = ProfileErrorMessage.AVATAR_URL_INVALID_FORMAT)
    @field:Size(max = 500, message = ProfileErrorMessage.AVATAR_URL_INVALID_LENGTH)
    override var avatarUrl: String? = null,

    @field:URL(message = ProfileErrorMessage.COVER_URL_INVALID_FORMAT)
    @field:Size(max = 500, message = ProfileErrorMessage.COVER_URL_INVALID_LENGTH)
    override var coverUrl: String? = null,

    @field:Size(max = 1000, message = ProfileErrorMessage.BIO_SIZE_INVALID)
    override var bio: String? = null,

    @field:Size(max = 100, message = ProfileErrorMessage.PRIMARY_CITY_SIZE_INVALID)
    @field:Pattern(
        regexp = "^[\\p{L}\\s\\-']+$",
        message = ProfileErrorMessage.PRIMARY_CITY_INVALID
    )
    override var primaryCity: String? = null,

    @field:Size(max = 2, message = ProfileErrorMessage.PRIMARY_STATE_SIZE_INVALID)
    @field:Pattern(
        regexp = "^[A-Z]{2}$",
        message = ProfileErrorMessage.PRIMARY_STATE_INVALID
    )
    override var primaryState: String? = null,
    override var visibility: Visibility = Visibility.PRIVATE,
    override val createdAt: Instant? = null,
    override val updatedAt: Instant? = null,
    override val deletedAt: Instant? = null
) : Profile