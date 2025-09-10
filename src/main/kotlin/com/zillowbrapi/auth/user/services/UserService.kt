package com.zillowbrapi.auth.user.services

import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.errors.UserErrorMessages
import com.zillowbrapi.auth.user.models.User
import com.zillowbrapi.auth.user.models.UserEntity
import com.zillowbrapi.auth.user.repositories.UserRepository
import com.zillowbrapi.auth.user.types.UserRequestType
import com.zillowbrapi.auth.user.types.UserRole
import com.zillowbrapi.auth.user.types.UserStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * Retrieves a list of all user entities from the database.
     * This operation is performed in a read-only transactional context.
     *
     * @return a list containing all user entities.
     */
    @Transactional(readOnly = true)
    fun listAll(): List<UserEntity> = repository.findAll()

    /**
     * Retrieves a user entity by its unique identifier.
     * Throws a `NoSuchElementException` if no user with the specified ID exists in the database.
     *
     * @param id the unique identifier of the user to be retrieved.
     * @return the user entity with the specified ID.
     * @throws NoSuchElementException if no user with the specified ID exists.
     */
    @Transactional(readOnly = true)
    fun getById(id: UUID): UserEntity =
        repository.findById(id).orElseThrow { NoSuchElementException("User $id not found") }

    /**
     * Creates a new user entity based on the provided user creation request.
     * Validates and normalizes the input data, then persists the new user to the database.
     *
     * @param req the user creation request containing the details of the user to be created.
     * @return the newly created user entity.
     * @throws IllegalArgumentException if validation of the input request fails.
     * @throws org.springframework.dao.DataIntegrityViolationException if a unique constraint, such as the email, is violated.
     */
    @Transactional
    fun create(req: UserCreateRequest): UserEntity {
        val validatedUser = validateAndNormalizeUserRequest(UserRequestType.Create(req))

        val userEntity = UserEntity(validatedUser).apply {
            password = passwordEncoder.encode(validatedUser.password!!)
        }

        return repository.save(userEntity)
    }

    /**
     * Updates an existing user entity with the provided updates. Validates and normalizes the input data,
     * then applies the updated fields to the existing user before persisting the changes to the database.
     *
     * @param id the unique identifier of the user to update.
     * @param req the user update request containing the fields to update.
     * @return the updated user entity after persisting the changes.
     * @throws NoSuchElementException if no user with the specified ID exists.
     * @throws IllegalArgumentException if validation of the update request fails.
     * @throws org.springframework.dao.DataIntegrityViolationException if a unique constraint, such as the email, is violated.
     */
    @Transactional
    fun update(id: UUID, req: UserUpdateRequest): UserEntity {
        val existingUser = getById(id)
        val validatedUser = validateAndNormalizeUserRequest(UserRequestType.Update(req, existingUser))

        validatedUser.password?.let {
            existingUser.password = passwordEncoder.encode(it)
        }

        existingUser.updateFrom(validatedUser)

        return repository.save(existingUser)
    }

    /**
     * Deletes a user entity identified by the provided unique ID.
     * If the user does not exist, an exception is thrown.
     * Ensures the operation is executed within a transactional context.
     *
     * @param id the unique identifier of the user to delete.
     * @throws NoSuchElementException if no user with the specified ID exists.
     */
    @Transactional
    fun delete(id: UUID) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("User $id not found")
        }
        repository.deleteById(id)
    }

    @Transactional
    fun changeVerifyByUserId(id: UUID) {
        val user = getById(id)

        check(!(user.isDeleted())) { UserErrorMessages.USER_IS_DELETED }
        check(user.isVerified == true) { UserErrorMessages.USER_ALREADY_IS_VERIFIED }

        user.isVerified = true
        repository.save(user)
    }

    @Transactional
    fun addNewRoleToUser(id: UUID, role: UserRole): UserEntity {
        val user = getById(id)
        user.roles?.add(role)
        return repository.save(user)
    }

    @Transactional
    fun removeRoleFromUser(id: UUID, role: UserRole): UserEntity {
        val user = getById(id)
        user.roles?.remove(role)
        return repository.save(user)
    }

    /**
     * Validates and normalizes a user request for creating or updating a user. Trims unnecessary whitespace
     * from fields, normalizes the email to lowercase, and applies validation rules based on the request type.
     * If validation passes, returns a normalized `User` object.
     *
     * @param requestType the type of user request, which can either be a Create request containing the user details
     *                    for a new user or an Update request with changes to an existing user.
     * @return a `User` instance with normalized field values.
     * @throws IllegalArgumentException if any required field is missing, blank, or invalid based on the request type.
     * @throws org.springframework.dao.DataIntegrityViolationException if the provided email already exists in the database.
     */
    private fun validateAndNormalizeUserRequest(requestType: UserRequestType): User {
        val user = when (requestType) {
            is UserRequestType.Create -> requestType.request
            is UserRequestType.Update -> requestType.request
        }

        return object : User {
            override val id: UUID? = user.id
            override val firstName: String? = user.firstName?.trim()
            override val lastName: String? = user.lastName?.trim()
            override val screenName: String? = user.screenName?.trim()
            override val photoUrl: String? = user.photoUrl?.trim()?.takeIf { it.isNotBlank() }
            override val password: String? = user.password?.trim()
            override val email: String? = user.email?.trim()?.lowercase()
            override val phone = user.phone?.trim()
            override val createdAt: Instant? = user.createdAt
            override val updatedAt: Instant? = user.updatedAt
            override val deletedAt: Instant? = user.deletedAt
            override val isVerified: Boolean? = user.isVerified
            override val emailVerified: Boolean? = user.emailVerified
            override val phoneVerified: Boolean? = user.phoneVerified
            override val status: UserStatus? = user.status
            override val roles: MutableSet<UserRole>? = user.roles
        }
    }
}