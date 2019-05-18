import java.io.Serializable;

public class Car implements Comparable<Car>, Serializable {

    private int key, carPlate, year;
    private String model;

    public Car(int key) {
        this.key = key;
    }

    public Car(int key, int carPlate, int year, String model) {
        this.key = key;
        this.carPlate = carPlate;
        this.year = year;
        this.model = model;
    }

    public int getKey() {
        return key;
    }

    public int getCarPlate() {
        return carPlate;
    }

    public int getYear() {
        return year;
    }

    public String getModel() {
        return model;
    }

    public void setCarPlate(int carPlate) {
        this.carPlate = carPlate;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int compareTo(Car o) {
        return Integer.compare(key, o.key);
    }
}
