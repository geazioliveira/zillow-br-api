package com.zillowbrapi.auth.user

import com.zillowbrapi.auth.user.dtos.UserCreateRequest
import com.zillowbrapi.auth.user.dtos.UserUpdateRequest
import com.zillowbrapi.auth.user.model.UserEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
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
            firstName = " John ",
            lastName = " Doe ",
            screenName = " jdoe ",
            email = " JOHN@EXAMPLE.COM ",
            password = " secret "
        )
        whenever(repository.save(any<UserEntity>())).thenAnswer {
            (it.arguments[0] as UserEntity).apply { id = "93fb00cc-1702-4c41-9fe1-d67b8a641dbd" }
        }

        val created = service.create(req)
        assertEquals("93fb00cc-1702-4c41-9fe1-d67b8a641dbd", created.id)
        assertEquals("john@example.com", created.email)
        assertEquals("enc(secret)", created.password)
        assertEquals("John", created.firstName)
        assertEquals("Doe", created.lastName)
        assertEquals("jdoe", created.screenName)
    }

    @Test
    fun `create succeeds event with invalid email since validation is at controller level`() {
        val req = UserCreateRequest(
            firstName = " John ",
            lastName = " Doe ",
            screenName = "jdoe",
            email = "invalid",
            password = "secret"
        )

        whenever(repository.save(any<UserEntity>())).thenAnswer {
            (it.arguments[0] as UserEntity).apply { id = "93fb00cc-1702-4c41-9fe1-d67b8a641dbd" }
        }

        val created = service.create(req)
        assertEquals("93fb00cc-1702-4c41-9fe1-d67b8a641dbd", created.id)
        assertEquals("invalid", created.email)
        assertEquals("enc(secret)", created.password)
    }

    @Test
    fun `update works fine with valid request`() {
        val existing = UserEntity(
            firstName = " John ",
            lastName = " Doe ",
            screenName = "jdoe",
            photoUrl = null,
            password = "enc(old)",
            email = "john@example.com"
        )
        whenever(repository.findById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")).thenReturn(Optional.of(existing))
        whenever(repository.existsByEmail("new@example.com")).thenReturn(true)
        whenever(repository.save(any<UserEntity>())).thenAnswer {
            (it.arguments[0] as UserEntity).apply { id = "93fb00cc-1702-4c41-9fe1-d67b8a641dbd" }
        }

        val req = UserUpdateRequest(email = " new@example.com ", photoUrl = null)
        val updated = service.update("93fb00cc-1702-4c41-9fe1-d67b8a641dbd", req)
        assertEquals("93fb00cc-1702-4c41-9fe1-d67b8a641dbd", updated.id)
        assertEquals("new@example.com", updated.email)
    }

    @Test
    fun `delete throws when user not found`() {
        whenever(repository.existsById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")).thenReturn(false)
        assertThrows(NoSuchElementException::class.java) {
            service.delete("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")
        }
    }

    @Test
    fun `delete succeeds when user exists`() {
        whenever(repository.existsById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")).thenReturn(true)
        doNothing().whenever(repository).deleteById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")
        service.delete("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")
        verify(repository).deleteById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")
    }

    @Test
    fun `getById throws when not found`() {
        whenever(repository.findById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")).thenReturn(Optional.empty())
        assertThrows(NoSuchElementException::class.java) {
            service.getById("93fb00cc-1702-4c41-9fe1-d67b8a641dbd")
        }
    }
}
