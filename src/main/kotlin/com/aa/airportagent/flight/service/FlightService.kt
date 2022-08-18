package com.aa.airportagent.flight.service

import Flight
import Status
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.OffsetDateTime

@Service
class FlightService {
    fun getFlight(id: Int): Flight {
        return Flight(id, LocalDate.now(), 1, "PHX", "DFW", "N180AA", Status.SCHEDULED,
            OffsetDateTime.now(), OffsetDateTime.now())
    }
}