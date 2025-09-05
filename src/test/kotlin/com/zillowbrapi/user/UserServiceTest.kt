package com.zillowbrapi.user

import com.zillowbrapi.user.dtos.UserCreateRequest
import com.zillowbrapi.user.dtos.UserUpdateRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

class UserServiceTest {

    private val repository: UserRepository = mock()
    private val passwordEncoder: PasswordEncoder = mock()
    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        service = UserService(repository, passwordEncoder)
        whenever(passwordEncoder.encode(any())).thenAnswer { "enc(${it.arguments[0]})" }
    }

    @Test
    fun `create succeeds with valid request`() {
        val req = UserCreateRequest(
            name = " John ",
            screenName = " jdoe ",
            email = " JOHN@EXAMPLE.COM ",
            password = " secret "
        )
        whenever(repository.existsByEmail("john@example.com")).thenReturn(false)
        whenever(repository.save(any())).thenAnswer { (it.arguments[0] as UserEntity).apply { id = 1L } }

        val created = service.create(req)
        assertEquals(1L, created.id)
        assertEquals("john@example.com", created.email)
        assertEquals("enc(secret)", created.password)
    }

    @Test
    fun `create throws on invalid email`() {
        val req = UserCreateRequest(
            name = "John",
            screenName = "jdoe",
            email = "invalid",
            password = "secret"
        )
        assertThrows(IllegalArgumentException::class.java) {
            service.create(req)
        }
    }

    @Test
    fun `update throws conflict when email exists and changed`() {
        val existing = UserEntity(
            id = 5L,
            name = "John",
            screenName = "jdoe",
            photoUrl = null,
            password = "enc(old)",
            email = "john@example.com"
        )
        whenever(repository.findById(5L)).thenReturn(Optional.of(existing))
        whenever(repository.existsByEmail("new@example.com")).thenReturn(true)

        val req = UserUpdateRequest(email = " new@example.com ", photoUrl = null)
        assertThrows(DataIntegrityViolationException::class.java) {
            service.update(5L, req)
        }
    }

    @Test
    fun `delete throws when user not found`() {
        whenever(repository.existsById(7L)).thenReturn(false)
        assertThrows(NoSuchElementException::class.java) {
            service.delete(7L)
        }
    }

    @Test
    fun `delete succeeds when user exists`() {
        whenever(repository.existsById(8L)).thenReturn(true)
        doNothing().whenever(repository).deleteById(8L)
        service.delete(8L)
        verify(repository).deleteById(8L)
    }

    @Test
    fun `getById throws when not found`() {
        whenever(repository.findById(9L)).thenReturn(Optional.empty())
        assertThrows(NoSuchElementException::class.java) {
            service.getById(9L)
        }
    }
}
