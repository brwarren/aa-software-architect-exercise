package com.aa.airportagent.flight.service

import Flight
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/flights")
class FlightController(val flightService: FlightService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE], path = ["/{id}"])
    fun getFlight(@PathVariable id: Int): Flight {
        return flightService.getFlight(id)
    }
}