package nbcc.termproject.dtos;

import java.time.LocalDate;

public class EventDTO {

    private int id;

    private LocalDate startDate;
    private LocalDate endDate;
    private int seatingDuration;
    private String name;
    private String description;
    private double price;
    private Integer layoutId;
    private Integer menuId;


    public EventDTO() {
    }

    public EventDTO(int id, LocalDate startDate, LocalDate endDate, int seatingDuration, String name, String description, double price, Integer layoutId, Integer menuId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seatingDuration = seatingDuration;
        this.name = name;
        this.description = description;
        this.price = price;
        this.layoutId = layoutId;
        this.menuId = menuId;
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

    public Integer getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Integer layoutId) {
        this.layoutId = layoutId;
    }

    public Integer getMenuId() {
        return menuId;
    }



}