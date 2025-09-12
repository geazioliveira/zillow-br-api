package com.zillowbrapi.auth.user.controllers

import com.zillowbrapi.auth.user.dtos.UserChangeRoleRequest
import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.models.entities.UserEntity
import com.zillowbrapi.auth.user.services.UserService
import com.zillowbrapi.auth.user.types.UserChangeRoleType
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
    fun get(@PathVariable id: UUID): UserEntity = service.getById(id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @Valid @RequestBody req: UserUpdateRequest): UserEntity = service.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = service.delete(id)

    @PatchMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeVerifyUser(@PathVariable id: UUID) = service.changeVerifyByUserId(id)

    @PatchMapping("/{id}/change-role")
    fun changeRoleForUser(@PathVariable id: UUID,
                          @Valid @RequestBody request: UserChangeRoleRequest): ResponseEntity<UserEntity> {
        var body: UserEntity? = null
        if (request.type == UserChangeRoleType.ADD) {
            body = service.addNewRoleToUser(id, request.role!!)
        } else if (request.type == UserChangeRoleType.REMOVE) {
            body = service.removeRoleFromUser(id, request.role!!)
        }

        return ResponseEntity.status(HttpStatus.OK).body(body)
    }
}