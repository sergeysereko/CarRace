package Car;
import java.util.Random;

public class RaceCarRunnable extends Car {

    private int passed;
    private int distance;
    private boolean isFinish;

    public RaceCarRunnable(String name, int maxSpeed, int passed, int distance, boolean isFinish) {
        super(name, maxSpeed);
        this.passed = passed;
        this.distance = distance;
        this.isFinish = isFinish;

    }
    public RaceCarRunnable(String name, int maxSpeed, int distance) {
        super(name, maxSpeed);
        this.distance = distance;
    }

    private int getRandomSpeed() {
        Random random = new Random();
        return random.nextInt((getMaxSpeed() / 2) + 1) + getMaxSpeed() / 2;
    }

    // Переопределение метода run()
    @Override
    public void run() {
        while (!isFinish) {
            int speed = getRandomSpeed();
            int timeInterval = 1000;

            passed += (speed * timeInterval) / 1000; // расчет пройденной дистанции в метрах

            if (passed >= distance) {
                passed = distance;
                isFinish = true;
            }

            System.out.println(getCarInfo(speed, passed, distance));

            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для формирования строки с информацией о состоянии машины
    private String getCarInfo(int speed, int progress, int totalDistance) {
        return getName() + " => speed: " + speed + "; progress: " + progress + "/" + totalDistance;
    }

}
