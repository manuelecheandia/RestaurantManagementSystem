package nbcc.termproject.entities;

import jakarta.persistence.*;


@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private int id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", foreignKey = @ForeignKey(name = "FK_MenuItem_Menu"))
    private Menu menu;

    public MenuItem() {
    }

    public MenuItem(int id, String name, String description, Menu menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
