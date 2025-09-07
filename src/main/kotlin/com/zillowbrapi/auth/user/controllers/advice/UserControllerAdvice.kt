package com.zillowbrapi.auth.user.controllers.advice

import com.zillowbrapi.auth.user.errors.UserDataIntegrityException
import com.zillowbrapi.auth.user.errors.UserErrorMessages
import com.zillowbrapi.auth.user.errors.UserNotFoundException
import com.zillowbrapi.auth.user.errors.UserValidationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message ?: UserErrorMessages.USER_NOT_FOUND)))

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict(ex: DataIntegrityViolationException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (ex.mostSpecificCause.message ?: ex.message ?: UserErrorMessages.CONFLICT)))

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to (ex.message ?: UserErrorMessages.USER_NOT_FOUND)))

    @ExceptionHandler(UserValidationException::class)
    fun handleUserValidation(ex: UserValidationException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to (ex.message ?: UserErrorMessages.USER_VALIDATION_ERROR)))

    @ExceptionHandler(UserDataIntegrityException::class)
    fun handlerUserDataIntegrity(ex: UserDataIntegrityException): ResponseEntity<Map<String, String>> =
        ResponseEntity.status(HttpStatus.CONFLICT).body(mapOf("error" to (ex.message ?: UserErrorMessages.CONFLICT)))
}