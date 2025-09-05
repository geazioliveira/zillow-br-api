package com.zillowbrapi.auth.user

import com.zillowbrapi.common.database.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

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

) : BaseEntity()