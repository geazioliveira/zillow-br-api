package com.zillowbrapi.auth.profile.dtos

import com.zillowbrapi.auth.profile.models.Profile
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable
import java.time.Instant
import java.util.*
import java.util.Collections.emptySet

/**
 * DTO for {@link com.zillowbrapi.auth.profile.models.AgentProfileEntity}
 */
data class AgentProfileEntityDto(
    @field:NotNull var id: UUID? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val deletedAt: Instant? = null,
    @field:NotNull @field:Size(min = 2, max = 2) @field:NotBlank var creciUf: String? = null,
    @field:NotNull @field:NotEmpty var creciNumber: String? = null,
    val yearsExperience: Int? = null,
    val specialties: MutableSet<String> = emptySet(),
    val languages: MutableSet<String> = mutableSetOf("pt-BR"),
    val serviceAreas: String? = null,
    val website: String? = null,
    val avgRating: Double = 0.0,
    val totalReviews: Int = 0,
    val closedDealsCount: Int = 0,
    val avgResponseTimeHours: Int? = null,
    @field:NotNull var profileId: UUID? = null,
    val profile: Profile? = null
) : Serializable