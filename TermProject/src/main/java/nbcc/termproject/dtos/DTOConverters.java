package nbcc.termproject.dtos;

import nbcc.termproject.entities.Event;
import nbcc.termproject.entities.Reservation;
import nbcc.termproject.entities.Seating;

import java.util.List;
import java.util.stream.Collectors;

public class DTOConverters {

    public static EventDTO convertToEventDTO(Event event) {
        if (event == null) return null;
        return new EventDTO(
                event.getId(),
                event.getStartDate(),
                event.getEndDate(),
                event.getSeatingDuration(),
                event.getName(),
                event.getDescription(),
                event.getPrice(),
                event.getLayout().getId(),
                event.getMenu().getId());
    }

    public static Event convertFromEventDTO(EventDTO eventDTO) {
        if (eventDTO == null) return null;
        return new Event(
                eventDTO.getId(),
                eventDTO.getStartDate(),
                eventDTO.getEndDate(),
                eventDTO.getSeatingDuration(),
                eventDTO.getName(),
                eventDTO.getDescription(),
                eventDTO.getPrice()
        );
    }

    public static ReservationDTO convertToReservationDTO(Reservation reservation) {
        if (reservation == null) return null;
        return new ReservationDTO(
                reservation.getId(),
                reservation.getSeating().getId(),
                reservation.getFirstName(),
                reservation.getLastName(),
                reservation.getEmailAddress(),
                reservation.getGroupSize(),
                reservation.getStatus()
        );
    }

    public static Reservation convertFromReservationDTO(ReservationDTO reservationDTO) {
        if (reservationDTO == null) return null;
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setFirstName(reservationDTO.getFirstName());
        reservation.setLastName(reservationDTO.getLastName());
        reservation.setEmailAddress(reservationDTO.getEmailAddress());
        reservation.setGroupSize(reservationDTO.getGroupSize());
        reservation.setStatus(reservationDTO.getStatus());
        // Important: Seating relationship must be handled separately
        return reservation;
    }

    public static SeatingDTO convertToSeatingDTO(Seating seating) {
        if (seating == null) return null;
        return new SeatingDTO(


                seating.getId(),
                seating.getStartDateAndTime(),
                convertToEventDTO(seating.getEvent()),
                seating.getReservation().stream().map(DTOConverters::convertToReservationDTO).collect(Collectors.toList())
        );
    }


    public static Seating convertFromSeatingDTO(SeatingDTO seatingDTO) {
        if (seatingDTO == null) return null;
        Seating seating = new Seating();
        seating.setId(seatingDTO.getId());
        seating.setStartDateAndTime(seatingDTO.getStartDateAndTime());
        // Set event and reservations if you manage to pass their IDs or fetch them separately
        return seating;
    }

    public static List<EventDTO> convertToEventDTOList(List<Event> events) {
        return events.stream().map(DTOConverters::convertToEventDTO).collect(Collectors.toList());
    }

    public static List<SeatingDTO> convertToSeatingDTOList(List<Seating> seatings) {
        return seatings.stream().map(DTOConverters::convertToSeatingDTO).collect(Collectors.toList());
    }
}