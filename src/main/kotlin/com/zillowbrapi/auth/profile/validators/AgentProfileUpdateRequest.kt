package com.zillowbrapi.auth.profile.validators

import com.zillowbrapi.auth.profile.models.AgentProfile
import com.zillowbrapi.auth.profile.models.Profile
import jakarta.validation.constraints.Size
import java.io.Serializable
import java.time.Instant
import java.util.*

/**
 * DTO for {@link com.zillowbrapi.auth.profile.models.AgentProfileEntity}
 */
data class AgentProfileUpdateRequest(
    @field:Size(min = 2, max = 2)
    override var creciUf: String? = null,
    override var creciNumber: String? = null,
    override val yearsExperience: Int? = null,
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