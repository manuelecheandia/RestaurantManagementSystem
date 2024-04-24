package nbcc.termproject.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class SeatingDTO {

    private int id;
    private LocalDateTime startDateAndTime;
    private EventDTO event; // Assuming EventDTO is already defined similarly.
    private List<ReservationDTO> reservations; // Assuming ReservationDTO is already defined similarly.

    public SeatingDTO() {
    }

    public SeatingDTO(int id, LocalDateTime startDateAndTime, EventDTO event, List<ReservationDTO> reservations) {
        this.id = id;
        this.startDateAndTime = startDateAndTime;
        this.event = event;
        this.reservations = reservations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public List<ReservationDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDTO> reservations) {
        this.reservations = reservations;
    }
}
