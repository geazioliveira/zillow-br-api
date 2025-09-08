package com.zillowbrapi.auth.profile.models

import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import com.zillowbrapi.auth.user.models.UserEntity
import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.*

@Entity
@Table(
    name = "profiles",
    indexes = [Index(name = "ux_profiles_user_unique", columnList = "user_id, type", unique = true)]
)
class ProfileEntity (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ProfileType = ProfileType.CONSUMER,
    var avatarUrl: String? = null,
    var coverUrl: String? = null,

    @Column(columnDefinition = "Text")
    var bio: String? = null,
    var primaryCity: String? = null,
    var primaryState: String? = null,
    var visibility: Visibility = Visibility.PRIVATE
) : BaseEntity() {
    constructor(profile: Profile) : this(
        user = profile.user ?: throw IllegalArgumentException("User cannot be null"),
        type = profile.type ?: ProfileType.CONSUMER,
        avatarUrl = profile.avatarUrl,
        coverUrl = profile.coverUrl,
        bio = profile.bio,
        primaryCity = profile.primaryCity,
        primaryState = profile.primaryState,
        visibility = profile.visibility ?: Visibility.PRIVATE
    )

    companion object {
        fun from(profile: Profile): ProfileEntity {
            return ProfileEntity(profile)
        }
    }
}