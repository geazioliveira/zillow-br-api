package com.zillowbrapi.user

import com.zillowbrapi.user.dtos.UserCreateRequest
import com.zillowbrapi.user.dtos.UserUpdateRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth/users")
class UserController(
    private val service: UserService
) {

    @PostMapping
    fun create(@Valid @RequestBody req: UserCreateRequest): ResponseEntity<UserEntity> {
        val created = service.create(req)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @GetMapping
    fun list(): List<UserEntity> = service.listAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): UserEntity = service.getById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody req: UserUpdateRequest): UserEntity = service.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = service.delete(id)
}

