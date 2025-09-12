package com.zillowbrapi.auth.profile.models.entities

import com.zillowbrapi.auth.profile.models.AgentProfile
import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "agent_profiles",
    indexes = [Index(name = "ux_agent_creci_unique", columnList = "creci_uf,creci_number", unique = true)]
)
class AgentProfileEntity (
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity,

    @Column(name = "creci_uf", nullable = false) var creciUf: String,
    @Column(name = "creci_number", nullable = false) var creciNumber: String,

    var yearsExperience: Int? = null,

    @ElementCollection
    @CollectionTable(name = "agent_specialties", joinColumns = [JoinColumn(name = "agent_profile_id")])
    @Column(name = "specialty")
    var specialties: Set<String> = emptySet(),

    @ElementCollection
    @CollectionTable(name = "agent_languages", joinColumns = [JoinColumn(name = "agent_profile_id")])
    @Column(name = "language")
    var languages: Set<String> = setOf("pt-BR"),

    @Column(columnDefinition = "text")
    var serviceAreas: String? = null,
    var website: String? = null,
    var avgRating: Double = 0.0,
    var totalReviews: Int = 0,
    var closedDealsCount: Int = 0,
    var avgResponseTimeHours: Int? = null
) : BaseEntity() {

    constructor(profile: AgentProfile) : this(
        yearsExperience = profile.yearsExperience,
        specialties = profile.specialties,
        languages = profile.languages,
        serviceAreas = profile.serviceAreas,
        website = profile.website,
        avgRating = profile.avgRating,
        totalReviews = profile.totalReviews,
        closedDealsCount = profile.closedDealsCount,
        avgResponseTimeHours = profile.avgResponseTimeHours,
        profile = ProfileEntity(),
        creciUf = checkNotNull(profile.creciUf) { "CRECI UF cannot be null" },
        creciNumber = checkNotNull(profile.creciNumber) { "CRECI number cannot be null" }
    )

    companion object {
        fun from(profile: AgentProfile): AgentProfileEntity {
            return AgentProfileEntity(profile)
        }
    }

    fun updateFrom(profile: AgentProfile): AgentProfileEntity {
        profile.profile?.let { this.profile = ProfileEntity.from(it) }
        profile.creciUf?.let { this.creciUf = it }
        profile.creciNumber?.let { this.creciNumber = it }
        profile.yearsExperience?.let { this.yearsExperience = it }
        profile.specialties.let { this.specialties = it }
        profile.languages.let { this.languages = it }
        profile.serviceAreas.let { this.serviceAreas = it }
        profile.website?.let { this.website = it }
        profile.avgRating.let { this.avgRating = it }
        profile.totalReviews.let { this.totalReviews = it }

        return this
    }

}