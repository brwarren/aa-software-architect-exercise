package com.aa.airportagent.flight

import Flight
import Status
import com.aa.airportagent.flight.service.FlightService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.localDate
import io.kotest.property.arbitrary.offsetDateTime
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.time.OffsetDateTime

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class FlightControllerTest: FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var flightService: FlightService

    init {
        test("GET /flights/{id} returns flight") {
            checkAll(Arb.int()) {
                    id ->
                val expected = Flight(id, LocalDate.now(), 1, "PHX", "DFW", "N180AA",
                    Status.SCHEDULED, OffsetDateTime.now(), OffsetDateTime.now())
                given(flightService.getFlight(id)).willReturn(expected)

                val response = mockMvc.perform(
                    MockMvcRequestBuilders.get("/flights/${id}")
                        .contentType(MediaType.APPLICATION_CBOR)
                )
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                    .andReturn()
                val result = objectMapper.readValue<Flight>(response.response.contentAsString)
                result shouldBe expected
            }
        }
    }
}