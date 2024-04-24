
package nbcc.termproject.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dining_table_id")
    private int id;

    private int  numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layout_id", foreignKey = @ForeignKey(name = "FK_Tables_LayOut"))
    private Layout layout;

    public DiningTable(int id, int numberOfSeats, Layout layout) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.layout = layout;
    }

    public DiningTable() {


    }

    public DiningTable(int id, int numberOfSeats) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}

