package com.zillowbrapi.auth.profile.models.entities

import com.zillowbrapi.auth.profile.types.ContactPreference
import com.zillowbrapi.auth.profile.types.EntityType
import com.zillowbrapi.auth.profile.types.VerificationStatus
import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "landlord_profiles")
class LandlordProfileEntity (
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: ProfileEntity,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var entityType: EntityType = EntityType.PERSON,

    var cpf: String? = null,
    var cnpj: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var contactPreference: ContactPreference = ContactPreference.CHAT,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var ownerVerificationStatus: VerificationStatus = VerificationStatus.UNVERIFIED
) : BaseEntity()