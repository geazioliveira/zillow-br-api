package com.zillowbrapi.auth.profile.repositories

import com.zillowbrapi.auth.profile.models.entities.AgentProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AgentProfileRepository : JpaRepository<AgentProfileEntity, UUID> {
    fun existsByCreciNumber(creciNumber: String): Boolean
    fun findByProfileId(profileId: UUID): AgentProfileEntity?
    @Query("SELECT a FROM AgentProfileEntity a JOIN FETCH a.profile WHERE a.id = :id")
    fun findByIdWithProfile(id: UUID): AgentProfileEntity?

    @Query("SELECT a FROM AgentProfileEntity a JOIN FETCH a.profile p WHERE p.user.id = :userId")
    fun findByUserId(userId: UUID): AgentProfileEntity?

    fun existsByCreciNumberAndCreciUf(creciNumber: String, creciUf: String): Boolean

    fun findByCreciNumber(creciNumber: String): AgentProfileEntity
}
