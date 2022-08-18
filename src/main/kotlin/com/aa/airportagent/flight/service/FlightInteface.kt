import java.lang.IllegalStateException
import java.time.LocalDate

interface FlightInterface {
    fun firstFlightOfDay(nose: String, date: LocalDate, flightList: List<Flight>): Flight?
}

class FlightInterfaceImp : FlightInterface {

    override fun firstFlightOfDay(nose: String, date: LocalDate, flightList: List<Flight>): Flight? {
        val fList = mutableListOf<Flight>()
        for (f in flightList) {
            if (f.nose == nose && f.date == date && f.status != Status.CANCELLED)
                fList.add(f)
        }
        fList.sortBy { it.departureTime() }
        if (fList.isEmpty())
            return null
        else
            return fList.first()
    }

    fun totalFlightsByStation(flights: List<Flight>): Map<String, Int> {
        return flights.groupingBy { it.origin }.eachCount().toMutableMap()
            .let { list ->
                flights
                    .groupingBy { it.destination }
                    .eachCountTo(list)
            }
    }

    fun assignNoseToFlights(flightList: List<Flight>, nose: String? = null) {
        flightList.forEach{
            if (it.isDeparted())
                throw IllegalStateException("Nose cannot be changed for departed flight")
            it.updateAircraft(nose)
        }
    }
}
