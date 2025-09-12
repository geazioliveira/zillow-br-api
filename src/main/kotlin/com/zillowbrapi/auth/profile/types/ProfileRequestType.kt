package com.zillowbrapi.auth.profile.types

import com.zillowbrapi.auth.profile.models.entities.ProfileEntity
import com.zillowbrapi.auth.profile.validators.ProfileCreateRequest
import com.zillowbrapi.auth.profile.validators.ProfileUpdateRequest

sealed class ProfileRequestType {
    data class CreateProfile(val request: ProfileCreateRequest) : ProfileRequestType()
    data class UpdateProfile(val request: ProfileUpdateRequest, val existingUser: ProfileEntity) : ProfileRequestType()
}