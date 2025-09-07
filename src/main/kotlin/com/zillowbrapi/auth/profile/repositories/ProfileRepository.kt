package com.zillowbrapi.auth.profile.repositories

import com.zillowbrapi.auth.profile.models.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<ProfileEntity, String>