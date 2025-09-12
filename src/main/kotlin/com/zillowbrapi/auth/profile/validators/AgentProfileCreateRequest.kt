package com.zillowbrapi.auth.profile.validators

import com.zillowbrapi.auth.profile.models.AgentProfile
import com.zillowbrapi.auth.profile.models.Profile
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.io.Serializable
import java.time.Instant
import java.util.*

/**
 * DTO for {@link com.zillowbrapi.auth.profile.models.AgentProfileEntity}
 */
data class AgentProfileCreateRequest(
    @field:NotNull @field:Size(min = 2, max = 2) @field:NotBlank
    override var creciUf: String? = null,
    @field:NotNull @field:NotEmpty
    override var creciNumber: String? = null,
    override val yearsExperience: Int? = null,
    @field:NotNull
    override var profileId: UUID? = null,
    override var id: UUID? = null,
    override val createdAt: Instant? = null,
    override val updatedAt: Instant? = null,
    override val deletedAt: Instant? = null,
    override val specialties: MutableSet<String> = mutableSetOf(),
    override val languages: MutableSet<String> = mutableSetOf(),
    override val serviceAreas: String? = null,
    override val website: String? = null,
    override val avgRating: Double = 0.0,
    override val totalReviews: Int = 0,
    override val closedDealsCount: Int = 0,
    override val avgResponseTimeHours: Int? = null,
    override val profile: Profile? = null
) : Serializable, AgentProfile