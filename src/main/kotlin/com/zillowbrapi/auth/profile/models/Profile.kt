package com.zillowbrapi.auth.profile.models

import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import com.zillowbrapi.auth.user.models.entities.UserEntity
import com.zillowbrapi.common.database.BaseModel
import java.util.*

interface Profile: BaseModel {
    val user: UserEntity?
    val userId: UUID?
    val type: ProfileType?
    val avatarUrl: String?
    val coverUrl: String?
    val bio: String?
    val primaryCity: String?
    val primaryState: String?
    val visibility: Visibility?
}