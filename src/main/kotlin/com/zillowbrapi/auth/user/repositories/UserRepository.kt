package com.zillowbrapi.auth.user.repositories

import com.zillowbrapi.auth.user.models.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface   UserRepository : JpaRepository<UserEntity, String> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?
}