package com.zillowbrapi.agents

import com.zillowbrapi.user.profile.agent.AgentProfileEntity
import com.zillowbrapi.user.profile.agent.AgentProfileRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class AgentPublicServiceTest {

    private val agentRepo: AgentProfileRepository = mock()
    private lateinit var service: AgentPublicService

    @BeforeEach
    fun setup() {
        service = AgentPublicService(agentRepo)
    }

    @Test
    fun `list maps entities to responses`() {
        whenever(agentRepo.findAll()).thenReturn(listOf(
            AgentProfileEntity(id = 1L, userId = 10L, creci = "CR1"),
            AgentProfileEntity(id = 2L, userId = 20L, creci = "CR2")
        ))
        val res = service.list()
        assertEquals(2, res.size)
        assertEquals("CR1", res[0].creci)
    }

    @Test
    fun `getById throws when not found`() {
        whenever(agentRepo.findById(any())).thenReturn(Optional.empty())
        assertThrows(NoSuchElementException::class.java) {
            service.getById(999)
        }
    }

    @Test
    fun `getByCreci returns when found`() {
        whenever(agentRepo.findByCreci("CRX")).thenReturn(AgentProfileEntity(id = 3L, userId = 30L, creci = "CRX"))
        val res = service.getByCreci("CRX")
        assertEquals(3L, res.id)
        assertEquals("CRX", res.creci)
    }
}
