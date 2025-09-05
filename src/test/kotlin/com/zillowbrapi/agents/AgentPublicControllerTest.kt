package com.zillowbrapi.agents

import com.zillowbrapi.me.dtos.AgentProfileResponse
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class AgentPublicControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var service: AgentPublicService

    @BeforeEach
    fun setup() {
        service = mock()
        val controller = AgentPublicController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(AgentPublicControllerAdvice())
            .build()
    }

    @Test
    fun `GET list returns 200`() {
        whenever(service.list()).thenReturn(listOf(
            AgentProfileResponse(1L, 11L, "CR1", null, null, null, null, null, null, null)
        ))
        mockMvc.perform(get("/api/agents"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", equalTo(1)))
    }

    @Test
    fun `GET by id 404 handled by advice`() {
        doThrow(NoSuchElementException("not found")).whenever(service).getById(any())
        mockMvc.perform(get("/api/agents/99"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").exists())
    }

    @Test
    fun `GET by creci returns 200`() {
        whenever(service.getByCreci("CRX")).thenReturn(
            AgentProfileResponse(2L, 22L, "CRX", null, null, null, null, null, null, null)
        )
        mockMvc.perform(get("/api/agents/by-creci/CRX"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.creci", equalTo("CRX")))
    }
}
