package com.zillowbrapi.user.model

import com.zillowbrapi.common.database.BaseModel

interface User : BaseModel {
    val firstName: String?
    val lastName: String?
    val screenName: String?
    val photoUrl: String?
    val password: String?
    val email: String?
}