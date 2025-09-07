package com.zillowbrapi.auth.user.models

import com.zillowbrapi.auth.user.types.UserRole
import com.zillowbrapi.auth.user.types.UserStatus
import com.zillowbrapi.common.database.BaseModel

interface User : BaseModel {
    val firstName: String?
    val lastName: String?
    val screenName: String?
    val photoUrl: String?
    val password: String?
    val email: String?
    val phone: String?
    val isVerified: Boolean?
    val emailVerified: Boolean?
    val phoneVerified: Boolean?
    val status: UserStatus?
    val roles: MutableSet<UserRole>?
}