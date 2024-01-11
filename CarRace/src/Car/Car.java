package Car;

public class Car implements Runnable{

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
        System.out.println("Car " + name + " is now running.");
        System.out.println("Max Speed: " + maxSpeed + " km/h");
    }
}
