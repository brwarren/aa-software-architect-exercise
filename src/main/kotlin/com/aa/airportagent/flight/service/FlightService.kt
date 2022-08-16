package com.aa.airportagent.flight.service

import Flight
import org.springframework.stereotype.Service

@Service
class FlightService {
    fun getFlight(id: Int): Flight {
        return Flight(id)
    }
}