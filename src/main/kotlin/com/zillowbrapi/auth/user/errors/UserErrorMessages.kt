package com.zillowbrapi.auth.user.errors

import org.springframework.dao.DataIntegrityViolationException

object UserErrorMessages {
    // Validation error messages
    const val FIRST_NAME_BLANK = "First Name cannot be blank"
    const val LAST_NAME_BLANK = "Last Name cannot be blank"
    const val USER_NOT_FOUND = "User not found"
    const val SCREEN_NAME_BLANK = "Screen name cannot be blank"
    const val PASSWORD_BLANK = "Password cannot be blank"
    const val EMAIL_BLANK = "Email cannot be blank"
    const val EMAIL_INVALID_FORMAT = "Email must be a valid email address"
    const val USER_VALIDATION_ERROR = "User validation error"
    const val CONFLICT = "Conflict"
    const val USER_ALREADY_IS_VERIFIED = "User is already verified"
    const val USER_IS_DELETED = "User is deleted"
}

class UserValidationException(message: String, cause: Throwable? = null) : IllegalArgumentException(message, cause)

class UserNotFoundException(message: String, cause: Throwable? = null) : NoSuchElementException(message, cause)

class UserDataIntegrityException(message: String, cause: Throwable? = null) : DataIntegrityViolationException(message, cause)