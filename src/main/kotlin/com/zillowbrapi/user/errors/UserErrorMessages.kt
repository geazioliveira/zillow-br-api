package com.zillowbrapi.user.errors

import org.springframework.dao.DataIntegrityViolationException

object UserErrorMessages {
    // Validation error messages
    const val FIRST_NAME_BLANK = "First Name cannot be blank"
    const val LAST_NAME_BLANK = "Last Name cannot be blank"
    const val SCREEN_NAME_BLANK = "Screen name cannot be blank"
    const val PASSWORD_BLANK = "Password cannot be blank"
    const val EMAIL_BLANK = "Email cannot be blank"
    const val EMAIL_INVALID_FORMAT = "Invalid email format"
    const val EMAIL_ALREADY_EXISTS = "Email already exists"
    const val USER_VALIDATION_ERROR = "User validation error"
    const val CONFLICT = "Conflict"

    // Not found error messages
    const val USER_NOT_FOUND = "User not found"

    // Templated messages for dynamic content
    fun emailInvalidFormat(email: String) = "Invalid email format: $email"
    fun emailAlreadyExists(email: String) = "Email already exists: $email"
    fun userNotFound(id: Long) = "User $id not found"
}

class UserValidationException(message: String, cause: Throwable? = null) : IllegalArgumentException(message, cause)

class UserNotFoundException(message: String, cause: Throwable? = null) : NoSuchElementException(message, cause)

class UserDataIntegrityException(message: String, cause: Throwable? = null) : DataIntegrityViolationException(message, cause)