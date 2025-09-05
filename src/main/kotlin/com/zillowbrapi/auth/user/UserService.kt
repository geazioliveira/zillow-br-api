package com.zillowbrapi.auth.user

import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.model.User
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

sealed class UserRequestType {
    data class Create(val request: UserCreateRequest) : UserRequestType()
    data class Update(val request: UserUpdateRequest, val existingUser: UserEntity) : UserRequestType()
}

@Service
class UserService(
    private val repository: UserRepository,
    private val passwordEncoder: org.springframework.security.crypto.password.PasswordEncoder
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
    fun getById(id: String): UserEntity =
        repository.findById(id).orElseThrow { NoSuchElementException("User $id not found") }

    /**
     * Creates a new user entity based on the provided user creation request.
     * Validates and normalizes the input data, then persists the new user to the database.
     *
     * @param req the user creation request containing the details of the user to be created.
     * @return the newly created user entity.
     * @throws IllegalArgumentException if validation of the input request fails.
     * @throws DataIntegrityViolationException if a unique constraint, such as the email, is violated.
     */
    @Transactional
    fun create(req: UserCreateRequest): UserEntity {
        val validatedUser = validateAndNormalizeUserRequest(UserRequestType.Create(req))

        val userEntity = UserEntity(
            firstName = validatedUser.firstName!!,
            lastName = validatedUser.lastName!!,
            email = validatedUser.email!!,
            screenName = validatedUser.screenName,
            photoUrl = validatedUser.photoUrl,
            password = passwordEncoder.encode(validatedUser.password!!),
        )

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
     * @throws DataIntegrityViolationException if a unique constraint, such as the email, is violated.
     */
    @Transactional
    fun update(id: String, req: UserUpdateRequest): UserEntity {
        val existingUser = getById(id)
        val validatedUser = validateAndNormalizeUserRequest(UserRequestType.Update(req, existingUser))

        // Apply only the provided fields
        validatedUser.firstName?.let { existingUser.firstName = it }
        validatedUser.lastName?.let { existingUser.lastName = it }
        validatedUser.email?.let { existingUser.email = it }
        validatedUser.screenName?.let { existingUser.screenName = it }
        validatedUser.photoUrl?.let { existingUser.photoUrl = it }
        validatedUser.password?.let { existingUser.password = passwordEncoder.encode(it) }

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
    fun delete(id: String) {
        if (!repository.existsById(id)) {
            throw NoSuchElementException("User $id not found")
        }
        repository.deleteById(id)
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
     * @throws DataIntegrityViolationException if the provided email already exists in the database.
     */
    private fun validateAndNormalizeUserRequest(requestType: UserRequestType): User {
        val user = when (requestType) {
            is UserRequestType.Create -> requestType.request
            is UserRequestType.Update -> requestType.request
        }

        val normalizedFirstName = user.firstName?.trim()
        val normalizedLastNameName = user.lastName?.trim()
        val normalizedEmail = user.email?.trim()?.lowercase()
        val normalizedScreenName = user.screenName?.trim()
        val normalizedPhotoUrl = user.photoUrl?.trim()?.takeIf { it.isNotBlank() }
        val normalizedPassword = user.password?.trim()

        return object : User {
            override val id: Long? = user.id
            override val firstName: String? = normalizedFirstName
            override val lastName: String? = normalizedLastNameName
            override val screenName: String? = normalizedScreenName
            override val photoUrl: String? = normalizedPhotoUrl
            override val password: String? = normalizedPassword
            override val email: String? = normalizedEmail
            override val createdAt: Instant? = user.createdAt
            override val updatedAt: Instant? = user.updatedAt
            override val deletedAt: Instant? = user.deletedAt
        }
    }
}


