package com.zillowbrapi.auth.profile.services

import com.zillowbrapi.auth.profile.dtos.AgentProfileEntityDto
import com.zillowbrapi.auth.profile.models.AgentProfileEntity
import com.zillowbrapi.auth.profile.repositories.AgentProfileRepository
import com.zillowbrapi.auth.profile.types.ProfileType
import com.zillowbrapi.auth.user.types.UserRole
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AgentProfileService(
    private val repository: AgentProfileRepository,
    private val profileService: ProfileService
) {

    @Transactional(readOnly = true)
    fun listAll(): List<AgentProfileEntity> = repository.findAll()

    fun getById(id: UUID): AgentProfileEntity {
        return repository.findById(id).orElseThrow {
            NoSuchElementException("Agent profile $id not found")
        }
    }
    @Transactional(readOnly = true)
    fun getByProfileId(profileId: UUID): AgentProfileEntity? {
        val agentProfile = repository.findByProfileId(profileId)
            ?: throw NoSuchElementException("Agent profile for profile $profileId not found")

        return agentProfile
    }

    @Transactional(readOnly = true)
    fun getByUserId(userId: UUID): AgentProfileEntity? {
        val agentProfile = repository.findByUserId(userId)
            ?: throw NoSuchElementException("Agent profile for user $userId not found")
        return agentProfile
    }

    @Transactional
    fun create(req: AgentProfileEntityDto): AgentProfileEntity {
        val profile = profileService.getById(req.profileId!!)

        // Validate profile type
        require(profile.type == ProfileType.AGENT) {
            "Profile must be type agent to create an agent profile"
        }
        val user = profile.user!!
        require(user.roles?.contains(UserRole.AGENT) == true) {
            "User must have agent role to create an agent profile"
        }
        // Check if agent profile already exists for this profile
        require(repository.findByProfileId(req.profileId!!) == null) {
            "Agent profile already exists for profile ${req.profileId}"
        }
        require(!(repository.existsByCreciNumberAndCreciUf(req.creciUf!!, req.creciNumber!!))) {
            "CRECI ${req.creciNumber} already exists for ${req.creciUf}"
        }

        return repository.save(AgentProfileEntity(req))
    }

    @Transactional
    fun update(id: UUID, req: AgentProfileEntityDto): AgentProfileEntity {
        val existingAgent = repository.findByIdWithProfile(id)
            ?: throw NoSuchElementException("Agent profile $id not found")

        // Validate CRECI uniques if updated
        if ((req.creciUf != null || req.creciNumber != null)) {
            val newCreciUf = req.creciUf ?: existingAgent.creciUf
            val creciNumber = req.creciNumber ?: existingAgent.creciNumber

            if ((newCreciUf != existingAgent.creciUf) || (creciNumber != existingAgent.creciNumber)) {
                repository.existsByCreciNumberAndCreciUf(creciNumber, newCreciUf)
                    || throw DataIntegrityViolationException("CRECI $creciNumber already exists for $newCreciUf")
            }
        }

        return repository.save(AgentProfileEntity(req).apply {
            this.id = existingAgent.id
            this.profile = existingAgent.profile
        })
    }

    @Transactional
    fun delete (id: UUID) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("Agent profile $id not found")
        }
        repository.deleteById(id)
    }

    @Transactional
    fun updateRatingStats(id: UUID, newRating: Double, totalReviews: Int) {
        val agentProfile = repository.findById(id).orElseThrow {
            NoSuchElementException("Agent profile $id not found")
        }

        agentProfile.avgRating = newRating
        agentProfile.totalReviews = totalReviews
        repository.save(agentProfile)
    }

    @Transactional
    fun incrementClosedDeals(id: UUID) {
        val agentProfile = repository.findById(id).orElseThrow {
            NoSuchElementException("Agent profile $id not found")
        }

        agentProfile.closedDealsCount++
        repository.save(agentProfile)
    }



}