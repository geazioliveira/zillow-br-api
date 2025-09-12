package com.zillowbrapi.auth.user.models.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.zillowbrapi.auth.profile.models.entities.ProfileEntity
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.models.User
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
    var password: String?,

    @Column(nullable = false, unique = true)
    var email: String?,

    @Column(nullable = true, length = 20)
    var phone: String? = null,

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean? = false,

    @Column(name = "email_verified", nullable = false)
    var emailVerified: Boolean? = false,

    @Column(name = "phone_verified", nullable = false)
    var phoneVerified: Boolean? = false,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: UserStatus? = UserStatus.ACTIVE,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var roles: MutableSet<UserRole>? = mutableSetOf(UserRole.CONSUMER)

) : BaseEntity() {

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    var profiles: MutableSet<ProfileEntity> = mutableSetOf()

    constructor(user: User) : this(
        firstName = user.firstName,
        lastName = user.lastName,
        screenName = user.screenName,
        photoUrl = user.photoUrl,
        password = user.password,
        email = user.email,
        phone = user.phone,
        isVerified = user.isVerified,
        emailVerified = user.emailVerified,
        phoneVerified = user.phoneVerified,
        status = user.status,
        roles = user.roles,
    )

    companion object {
        fun from(user: User, encodedPassword: String): UserEntity {
            return UserEntity(user).apply { password = encodedPassword }
        }
    }


    /**
     * Updates the current UserEntity instance with the non-null values from the provided User instance.
     * The method assigns values from the User instance to the corresponding fields in the UserEntity.
     *
     * @param user the User instance containing the values to update the current UserEntity fields
     * @return the updated UserEntity instance
     */
    fun updateFrom(user: User): UserEntity {
        user.firstName?.let { firstName = it }
        user.lastName?.let { lastName = it }
        user.screenName?.let { screenName = it }
        user.photoUrl?.let { photoUrl = it }
        user.password?.let { password = it }
        user.email?.let { email = it }
        user.phone?.let { phone = it }
        user.isVerified?.let { isVerified = it }
        user.emailVerified?.let { emailVerified = it }
        user.phoneVerified?.let { phoneVerified = it }
        user.status?.let { status = it }
        user.roles?.let { roles = it }

        return this
    }

    fun updateFrom(request: UserUpdateRequest): UserEntity = updateFrom(request as User)

}