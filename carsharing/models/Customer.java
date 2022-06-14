package carsharing.models;

public class Customer {
    private int id;
    private String name;
    private int customer_car_id = -1;

    public Customer(int id, String name, int customer_car_id) {
        this.id = id;
        this.name = name;
        this.customer_car_id = customer_car_id;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

    public int getCustomer_car_id() {
        return customer_car_id;
    }

    public boolean isCarRented() {
        return customer_car_id != 0;
    }

    public void setCustomer_car_id(int customer_car_id) {
        this.customer_car_id = customer_car_id;
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
}
