package com.zillowbrapi.auth.profile.repositories

import com.zillowbrapi.auth.profile.models.entities.ProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfileRepository : JpaRepository<ProfileEntity, UUID>