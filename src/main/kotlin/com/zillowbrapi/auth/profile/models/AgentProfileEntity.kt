package com.zillowbrapi.auth.profile.models

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

    @Column(columnDefinition = "jsonb") var serviceAreas: String? = null,
    var website: String? = null,

    var avgRating: Double = 0.0,
    var totalReviews: Int = 0,
    var closedDealsCount: Int = 0,
    var avgResponseTimeHours: Int? = null
) : BaseEntity()