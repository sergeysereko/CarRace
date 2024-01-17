package Car;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class RaceCarRunnable extends Car {
    private int passed;
    private int distance;
    private boolean isFinish;
    private Thread thread;
    private CountDownLatch finishLatch;
    private long finishTime;

    // Конструктор
    public RaceCarRunnable(String name, int maxSpeed, int distance, CountDownLatch finishLatch) {
        super(name, maxSpeed);
        this.passed = 0;
        this.distance = distance;
        this.isFinish = false;
        this.thread = new Thread(this);
        this.finishLatch = finishLatch;
    }

    // Геттер для потока
    public Thread getThread() {
        return thread;
    }

    // Сеттер для latch
    public void setStartLatch(CountDownLatch startLatch) {
        this.finishLatch = startLatch;
    }

    // Геттер для флага завершения гонки
    public boolean isFinished() {
        return isFinish;
    }

    // Геттер для времени финиша
    public long getFinishTime() {
        return finishTime;
    }

    // Метод, возвращающий случайную скорость
    private int getRandomSpeed() {
        Random random = new Random();
        return random.nextInt(getMaxSpeed() / 2) + getMaxSpeed() / 2;
    }

    public int getPassed() {
        return passed;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public CountDownLatch getFinishLatch() {
        return finishLatch;
    }


    @Override
    public void run() {
        try {
            finishLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!isFinish) {
            int speed = getRandomSpeed();
            int timeInterval = 1;

            // Расчет пройденной дистанции
            int distanceCovered = speed * timeInterval;
            passed += distanceCovered;

            if (passed >= distance) {
                passed = distance;
                isFinish = true;
                finishTime = System.currentTimeMillis() - Race.startRaceTime.get();
                finishLatch.countDown();
            }

            System.out.println(getName() + " => speed: " + speed + "; progress: " + passed + "/" + distance);

            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(getName() + " has finished the race!");
    }
}
