package com.zillowbrapi.auth.user.model

import com.zillowbrapi.auth.user.types.UserRole
import com.zillowbrapi.auth.user.types.UserStatus
import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class UserEntity(

    @Column(nullable = false)
    var firstName: String? = null,

    @Column(nullable = false)
    var lastName: String? = null,

    @Column(nullable = false)
    var screenName: String? = null,

    @Column(nullable = true)
    var photoUrl: String? = null,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = true, length = 20)
    var phone: String? = null,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false,

    @Column(name = "email_verified", nullable = false)
    var emailVerified: Boolean = false,

    @Column(name = "phone_verified", nullable = false)
    var phoneVerified: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus = UserStatus.ACTIVE,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var roles: MutableSet<UserRole> = mutableSetOf(UserRole.CONSUMER)

) : BaseEntity()