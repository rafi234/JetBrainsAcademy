package carsharing.models;

public class Car {
    private int id;
    private String name;
    private int fk_car_comp;

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Car(int id, String name, int fk_car_comp) {
        this.id = id;
        this.name = name;
        this.fk_car_comp = fk_car_comp;
    }

    @Override
    public String toString() {
        return id + ". " + name + " || " + fk_car_comp;
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

    public int getFk_car_comp() {
        return fk_car_comp;
    }

    public void setFk_car_comp(int fk_car_comp) {
        this.fk_car_comp = fk_car_comp;
    }
}
