package nbcc.termproject.entities;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDateTime;

@Entity
public class Seating {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "seating_id")
    private int id;



    private LocalDateTime startDateAndTime;

    public Seating() {
    }

    public Seating(LocalDateTime startDateAndTime, Event event, List<Reservation> reservation) {
        this.startDateAndTime = startDateAndTime;
        this.event = event;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", foreignKey = @ForeignKey(name = "FK_Seating_Event"))
    private Event event;

    @OneToMany(mappedBy = "seating", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private List<Reservation> reservation;


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(LocalDateTime startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }
}
