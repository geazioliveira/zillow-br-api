package com.zillowbrapi.auth.profile.models.entities

import com.zillowbrapi.auth.profile.types.Stage
import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "consumer_profiles")
class ConsumerProfileEntity (
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var stage: Stage = Stage.BROWSING,

    @Column(precision = 16, scale = 2)
    var budgetMin: BigDecimal? = null,

    @Column(precision = 16, scale = 2)
    var budgetMax: BigDecimal? = null,

    @ElementCollection
    @CollectionTable(name = "consumer_property_types", joinColumns = [JoinColumn(name = "consumer_profile_id")])
    @Column(name = "property_type")
    var desiredPropertyTypes: Set<String> = emptySet(),

    @Column(columnDefinition = "jsonb")
    var desiredCities: String? = null,

    var householdSize: Int? = null,
    var hasPets: Boolean? = null,
    var moveInDate: LocalDate? = null
) : BaseEntity()