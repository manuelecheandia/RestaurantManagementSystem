package nbcc.termproject.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "layout_id")
    private int id;

    private String name;

    private String description;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "layout", cascade = CascadeType.ALL)
    private List<DiningTable> tables;
    public Layout() {
    }

    public Layout(int id, String name, String description, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<DiningTable> getTables() {
        return tables;
    }

    public void setTables(List<DiningTable> tables) {
        this.tables = tables;
    }

}


