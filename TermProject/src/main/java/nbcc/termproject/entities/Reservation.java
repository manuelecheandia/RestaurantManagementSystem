package nbcc.termproject.entities;

import jakarta.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReservationId")
    private int id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seating_id", foreignKey = @ForeignKey(name = "FK_Reservation_Seating"))
    private Seating seating;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", foreignKey = @ForeignKey(name = "FK_Reservation_Table"))
    private DiningTable diningTable;

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    public Seating getSeating() {
        return seating;
    }

    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    private String firstName;

    private String lastName;

    private String emailAddress;

    private int groupSize;

    private String status;

    public Reservation() {
    }


    public Reservation(int id, Seating seating, String firstName, String lastName, String emailAddress, int groupSize, String status) {
        this.id = id;
        this.seating = seating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.groupSize = groupSize;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
