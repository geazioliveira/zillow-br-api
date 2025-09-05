package com.zillowbrapi.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface   UserRepository : JpaRepository<UserEntity, String> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?
}