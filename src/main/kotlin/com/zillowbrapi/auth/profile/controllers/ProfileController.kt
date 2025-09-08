package com.zillowbrapi.auth.profile.controllers

import com.zillowbrapi.auth.profile.dtos.ProfileCreateRequest
import com.zillowbrapi.auth.profile.dtos.ProfileUpdateRequest
import com.zillowbrapi.auth.profile.models.ProfileEntity
import com.zillowbrapi.auth.profile.services.ProfileService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/auth/profiles")
class ProfileController (
    private val service: ProfileService
) {
    @GetMapping
    fun listAll(): List<ProfileEntity> = service.listAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ProfileEntity = service.getById(id)

    @PostMapping
    fun create(
        @Valid @RequestBody req: ProfileCreateRequest
    ): ResponseEntity<ProfileEntity> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.create(req))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody req: ProfileUpdateRequest
    ) : ProfileEntity = service.update(id, req)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) = service.delete(id)
}