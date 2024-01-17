package Car;

public class Car implements Runnable {
    private String name;
    private int maxSpeed;


    public Car(String name, int maxSpeed) {
        this.name = name;
        this.maxSpeed = maxSpeed;
    }

    public String getName() {
        return name;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void run() {
        System.out.println("Car " + name + " is now running with max speed: " + maxSpeed + " km/h");
    }
}