import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

class FlightInterfaceTest : FreeSpec({
    val flightInterface = FlightInterfaceImp()
    "totalFlightsByStation" {
        var flights = listOf(
            Flight(1, LocalDate.of(2022, 1, 1), 1, "PHX", "DFW", "001", Status.SCHEDULED,
                OffsetDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2022, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
            ),
            Flight(2, LocalDate.of(2022, 1, 1), 2, "DFW", "CLT", "001", Status.SCHEDULED,
                OffsetDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2022, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        flightInterface.totalFlightsByStation(flights) shouldBe mapOf("PHX" to 1, "DFW" to 2, "CLT" to 1)
    }
    "firstFlightOfDay" - {
        val flight1 = Flight(1, LocalDate.of(2022, 1, 1), 1, "PHX", "DFW", "001", Status.SCHEDULED,
            OffsetDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
        )
        val flight2 = Flight(2, LocalDate.of(2022, 1, 1), 2, "DFW", "CLT", "001", Status.SCHEDULED,
            OffsetDateTime.of(2022, 1, 1, 17, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 1, 1, 19, 0, 0, 0, ZoneOffset.UTC)
        )
        val flight3 = Flight(3, LocalDate.of(2022, 1, 2), 1, "PHX", "DFW", "001", Status.SCHEDULED,
            OffsetDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
        )
        val flight4 = Flight(4, LocalDate.of(2022, 1, 2), 2, "PHX", "DFW", "002", Status.SCHEDULED,
            OffsetDateTime.of(2022, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2022, 1, 1, 15, 0, 0, 0, ZoneOffset.UTC)
        )
        "nose and date" {
            val flights = listOf(flight1, flight2, flight3, flight4)
            flightInterface.firstFlightOfDay("001", LocalDate.of(2022, 1, 1), flights) shouldBe flight1
        }
        "updated departure" {
            flight1.actualDepartureTime = OffsetDateTime.of(2022, 1, 1, 18, 1, 0, 0, ZoneOffset.UTC)
            val flights = listOf(flight1)
            flightInterface.firstFlightOfDay("001", LocalDate.of(2022, 1, 1), flights) shouldBe flight1
        }
        "cancelled flight" {
            flight1.status = Status.CANCELLED
            val flights = listOf(flight1, flight2, flight3, flight4)
            flightInterface.firstFlightOfDay("001", LocalDate.of(2022, 1, 1), flights) shouldBe flight2
        }
    }
})