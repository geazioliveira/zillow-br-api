package com.zillowbrapi.auth.login

import com.zillowbrapi.auth.user.models.entities.UserEntity
import com.zillowbrapi.auth.user.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val repository: UserRepository,
    private val passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder
) {

    @Transactional
    fun login(email: String, password: String): UserEntity? {
        val user = repository.findByEmail(email) ?: return null

        if (!passwordEncoder.matches(password, user.password)) {
            return null
        }

        return user
    }

}