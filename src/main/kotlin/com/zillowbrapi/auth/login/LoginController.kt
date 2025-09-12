package com.zillowbrapi.auth.login

import com.zillowbrapi.auth.login.dtos.LoginRequest
import com.zillowbrapi.auth.user.models.entities.UserEntity
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/login")
class LoginController(
    private val service: LoginService
) {

    @PostMapping
    fun login(@Valid @RequestBody req: LoginRequest): ResponseEntity<UserEntity> {
        val user = service.login(req.email!!, req.password!!) ?: return ResponseEntity.badRequest().build()

        return ResponseEntity.ok(user)
    }

}