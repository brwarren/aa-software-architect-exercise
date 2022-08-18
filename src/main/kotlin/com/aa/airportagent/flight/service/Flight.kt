import java.time.LocalDate
import java.time.OffsetDateTime

data class Flight(
    val id: Int,
    val date: LocalDate,
    val number: Int,
    val origin: String,
    val destination: String,
    var nose: String? = null,
    var status: Status,
    val scheduledDeparture: OffsetDateTime,
    val scheduledArrival: OffsetDateTime
) {
    var actualDepartureTime: OffsetDateTime? = null
        set(value) {
            field = value
            status = Status.OUT
        }

    var actualArrivalTime: OffsetDateTime? = null
        set(value) {
            field = value
            status = Status.IN
        }

    fun getArrivalDate(): LocalDate {
        if (actualArrivalTime != null)
            return actualArrivalTime!!.toLocalDate()
        else
            return scheduledArrival.toLocalDate()
    }

    fun departureTime(): OffsetDateTime =
        actualDepartureTime ?: scheduledDeparture

    val flightStatus: String get() =
        if (status == Status.SCHEDULED)
            "Scheduled"
        else if (status == Status.OUT)
            "Out"
        else if (status == Status.OFF)
            "Off"
        else if (status == Status.ON)
            "On"
        else if (status == Status.IN)
            "In"
        else
            "Cancelled"

    fun updateAircraft(a: String?) {
        nose = a
    }

    fun isDeparted(): Boolean {
        return (status === Status.OUT || status === Status.OFF)
    }

    fun isArrived(): Boolean {
        return (status === Status.ON || status === Status.IN)
    }

    override fun toString(): String {
        return "Flight(id=$id, flightDate=$date, number=$number, origin=$origin, " +
                "destination=$destination, nose=$nose, status=$status, scheduledDeparture=$scheduledDeparture, scheduledArrival=$scheduledArrival, " +
                "actualDepartureTime=$actualDepartureTime, actualArrivalTime=$actualArrivalTime)"
    }
}

enum class Status{
    SCHEDULED,
    OUT,
    OFF,
    ON,
    IN,
    CANCELLED
}
