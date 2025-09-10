package com.zillowbrapi.auth.profile.services

import com.zillowbrapi.auth.profile.dtos.ProfileCreateRequest
import com.zillowbrapi.auth.profile.dtos.ProfileUpdateRequest
import com.zillowbrapi.auth.profile.models.Profile
import com.zillowbrapi.auth.profile.models.ProfileEntity
import com.zillowbrapi.auth.profile.repositories.ProfileRepository
import com.zillowbrapi.auth.profile.types.ProfileRequestType
import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.profile.types.Visibility
import com.zillowbrapi.auth.user.models.UserEntity
import com.zillowbrapi.auth.user.services.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class ProfileService(
    private val repository: ProfileRepository,
    private val userService: UserService,
) {
    @Transactional(readOnly = true)
    fun listAll(): List<ProfileEntity> = repository.findAll()

    fun getById(id: UUID): ProfileEntity =
        repository.findById(id).orElseThrow {
            NoSuchElementException("Profile $id not found")
        }

    @Transactional
    fun create(req: ProfileCreateRequest): ProfileEntity {
        val user = userService.getById(req.userId!!)

        check(!user.isDeleted()) { "User ${user.id} is deleted" }

        val validateProfile = validateAndNormalizeProfileRequest(ProfileRequestType.CreateProfile(req), user)
        val profileEntity = ProfileEntity(validateProfile).apply {
            this.user = user
        }

        return repository.save(profileEntity)
    }

    @Transactional
    fun update(id: UUID, req: ProfileUpdateRequest): ProfileEntity {
        val existingProfile = getById(id)
        val user = existingProfile.user // Get user from existing profile
        val validateProfile =
            validateAndNormalizeProfileRequest(ProfileRequestType.UpdateProfile(req, existingProfile), user!!)

        return repository.save(ProfileEntity(validateProfile).apply {
            this.id = existingProfile.id
            this.user = user
        })
    }

    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Profile $id not found")
        }
        repository.deleteById(id)
    }


    private fun validateAndNormalizeProfileRequest(requestType: ProfileRequestType, user: UserEntity): Profile {
        val profile = when (requestType) {
            is ProfileRequestType.CreateProfile -> requestType.request
            is ProfileRequestType.UpdateProfile -> requestType.request
        }

        return object : Profile {
            override val id: UUID? = profile.id
            override val userId: UUID? = profile.userId
            override val user: UserEntity = user
            override val type: ProfileType? = profile.type
            override val avatarUrl: String? = profile.avatarUrl?.trim()?.takeIf { it.isNotBlank() }
            override val coverUrl: String? = profile.coverUrl?.trim()?.takeIf { it.isNotBlank() }
            override val bio: String? = profile.bio?.trim()?.takeIf { it.isNotBlank() }
            override val primaryCity: String? = profile.primaryCity?.trim()?.takeIf { it.isNotBlank() }
            override val primaryState: String? = profile.primaryState?.trim()?.takeIf { it.isNotBlank() }
            override val visibility: Visibility? = profile.visibility
            override val createdAt: Instant? = profile.createdAt
            override val updatedAt: Instant? = profile.updatedAt
            override val deletedAt: Instant? = profile.deletedAt
        }
    }
}