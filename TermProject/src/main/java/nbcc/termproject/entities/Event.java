package nbcc.termproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int seatingDuration; //Duration in minutes

    private String name;

    private String description;

    private double price;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seating> seatings;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "layout_id", foreignKey = @ForeignKey(name = "FK_Event_Layout"))
    @NotNull(message = "Layout is required")
    private Layout layout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", foreignKey = @ForeignKey(name = "FK_Event_Menu"))
    @NotNull(message = "Menu is required")
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Event() {
        this.seatings = new ArrayList<>();
    }

    public Event(int id, LocalDate startDate, LocalDate endDate, int seatingDuration, String name, String description, double price, Menu menu) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seatings = new ArrayList<>();
        this.menu = menu;
    }

    public Event(int id, LocalDate startDate, LocalDate endDate, int seatingDuration, String name, String description, double price) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getSeatingDuration() {
        return seatingDuration;
    }

    public void setSeatingDuration(int seatingDuration) {
        this.seatingDuration = seatingDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Seating> getSeatings() {
        return seatings;
    }

    public void setSeatings(List<Seating> seatings) {
        this.seatings = seatings;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
