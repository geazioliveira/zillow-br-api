package com.zillowbrapi.auth.profile.validators

import com.zillowbrapi.auth.profile.models.Profile
import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import com.zillowbrapi.auth.user.models.UserEntity
import java.time.Instant
import java.util.*

class ProfileResponse (
    override val user: UserEntity?,
    override val userId: UUID?,
    override val type: ProfileType?,
    override val avatarUrl: String?,
    override val coverUrl: String?,
    override val bio: String?,
    override val primaryCity: String?,
    override val primaryState: String?,
    override val visibility: Visibility?,
    override val id: UUID?,
    override val createdAt: Instant?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
) : Profile