package com.zillowbrapi.auth.profile.controllers

import com.zillowbrapi.auth.profile.dtos.AgentProfileEntityDto
import com.zillowbrapi.auth.profile.models.AgentProfileEntity
import com.zillowbrapi.auth.profile.services.AgentProfileService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/auth/profiles/agents")
class AgentProfileController (
    private val agentProfileService: AgentProfileService
) {
    @GetMapping
    fun listAll(): List<AgentProfileEntity> = agentProfileService.listAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): AgentProfileEntity =
        agentProfileService.getById(id)

    @GetMapping("/profile/{profileId}")
    fun getByProfileId(@PathVariable profileId: UUID): AgentProfileEntity? =
        agentProfileService.getByProfileId(profileId)

    @GetMapping("/user/{userId}")
    fun getByUserId(@PathVariable userId: UUID): AgentProfileEntity? =
        agentProfileService.getByUserId(userId)

    @PostMapping
    fun create(@Valid
    @RequestBody data: AgentProfileEntityDto): ResponseEntity<AgentProfileEntity> {
        return ResponseEntity.status(201).body(agentProfileService.create(data))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody data: AgentProfileEntityDto
    ): AgentProfileEntity = agentProfileService.update(id, data)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = agentProfileService.delete(
        id
    )

    @PatchMapping("/{id}/rating")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateRatingStats(
        @PathVariable id: UUID,
        @RequestParam newRating: Double,
        @RequestParam totalReviews: Int
    ) = agentProfileService.updateRatingStats(
        id, newRating, totalReviews
    )

    @PatchMapping("/{id}/deals/increment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun incrementClosedDeals(@PathVariable id: UUID) = agentProfileService.incrementClosedDeals(id)
}