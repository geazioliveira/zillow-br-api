package com.zillowbrapi.auth.profile.types

import com.zillowbrapi.auth.profile.dtos.ProfileCreateRequest
import com.zillowbrapi.auth.profile.dtos.ProfileUpdateRequest
import com.zillowbrapi.auth.profile.models.ProfileEntity

sealed class ProfileRequestType {
    data class CreateProfile(val request: ProfileCreateRequest) : ProfileRequestType()
    data class UpdateProfile(val request: ProfileUpdateRequest, val existingUser: ProfileEntity) : ProfileRequestType()
}