package com.zillowbrapi.auth.profile.models

import java.time.Instant
import java.util.*

/**
 * DTO for {@link com.zillowbrapi.auth.profile.models.AgentProfileEntity}
 */
interface AgentProfile {
    var id: UUID?
    val createdAt: Instant?
    val updatedAt: Instant?
    val deletedAt: Instant?
    var creciUf: String?
    var creciNumber: String?
    val yearsExperience: Int?
    val specialties: MutableSet<String>
    val languages: MutableSet<String>
    val serviceAreas: String?
    val website: String?
    val avgRating: Double
    val totalReviews: Int
    val closedDealsCount: Int
    val avgResponseTimeHours: Int?
    var profileId: UUID?
    val profile: Profile?
}