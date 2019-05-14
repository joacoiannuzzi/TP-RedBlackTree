import java.io.Serializable;

public class Car implements Comparable<Car>, Serializable {

    private int key, carPatent, year;
    private String model;

    public Car(int key) {
        this.key = key;
    }

    public Car(int key, int carPatent, int year, String model) {
        this.key = key;
        this.carPatent = carPatent;
        this.year = year;
        this.model = model;
    }

    public int getKey() {
        return key;
    }

    public int getCarPatent() {
        return carPatent;
    }

    public int getYear() {
        return year;
    }

    public String getModel() {
        return model;
    }

    public void setCarPatent(int carPatent) {
        this.carPatent = carPatent;
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
