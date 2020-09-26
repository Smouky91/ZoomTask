package Pages;

import java.time.LocalTime;

public class Journey {
    private String departureTime;
    private String arrivalTime;
    private int numberOfStops;
    private LocalTime distance;
    private float price;

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(int numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public LocalTime getDistance() {
        return distance;
    }

    public void setDistance(LocalTime distance) {
        this.distance = distance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", numberOfStops=" + numberOfStops +
                ", distance=" + distance +
                ", price=" + price +
                '}';
    }
}
