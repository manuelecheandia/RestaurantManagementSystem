package nbcc.termproject.dtos;

public class ReservationDTO {

    private int id;
    private int seatingId; // Store only the seating ID for simplicity
    private String firstName;
    private String lastName;
    private String emailAddress;
    private int groupSize;
    private String status;

    public ReservationDTO() {
    }

    public ReservationDTO(int id, int seatingId, String firstName, String lastName, String emailAddress, int groupSize, String status) {
        this.id = id;
        this.seatingId = seatingId;
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

    public int getSeatingId() {
        return seatingId;
    }

    public void setSeatingId(int seatingId) {
        this.seatingId = seatingId;
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
