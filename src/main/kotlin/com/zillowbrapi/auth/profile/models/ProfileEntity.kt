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
    var user: UserEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ProfileType,
    var avatarUrl: String? = null,
    var coverUrl: String? = null,
    var bio: String? = null,
    var primaryCity: String? = null,
    var primaryState: String? = null,
    var visibility: Visibility = Visibility.PRIVATE
) : BaseEntity()