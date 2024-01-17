package Car;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Race {
    public static AtomicLong startRaceTime = new AtomicLong(System.currentTimeMillis());

    public static void main(String[] args) {
        // Создание списка машин
        List<RaceCarRunnable> cars = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(cars.size());

        cars.add(new RaceCarRunnable("Ferrari", 250, 500, latch));
        cars.add(new RaceCarRunnable("Mercedes", 280, 500, latch));
        cars.add(new RaceCarRunnable("BMW", 270, 500, latch));
        cars.add(new RaceCarRunnable("Lotus", 290, 500, latch));
        cars.add(new RaceCarRunnable("McLaren", 295, 500, latch));

        // Старт гонки
        startRace(cars);

        // Ожидание завершения всех потоков
        for (RaceCarRunnable car : cars) {
            try {
                car.getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Вывод результатов гонки
        for (RaceCarRunnable car : cars) {
            System.out.println(car.getName() + " finished in " + convertToTime(car.getFinishTime()) + " seconds.");
        }

        RaceCarRunnable winner = getWinner(cars);
        System.out.println("\nThe winner is: " + winner.getName() + " with a time of " + convertToTime(winner.getFinishTime()));
    }

    // Статический метод для старта гонки
    public static void startRace(List<RaceCarRunnable> cars) {
        CountDownLatch startLatch = new CountDownLatch(1);

        // Отсчет до старта
        for (int i = 3; i > 0; i--) {
            System.out.println(i + "...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Get ready for the race!");
        try {
            Thread.sleep(500); // Задержка
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Запуск потоков
        for (RaceCarRunnable car : cars) {
            car.setStartLatch(startLatch);
            car.getThread().start();
        }

        try {
            startRaceTime.set(System.currentTimeMillis());
            startLatch.countDown(); // Старт гонки
            System.out.println("GO!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // определение победителя
    private static RaceCarRunnable getWinner(List<RaceCarRunnable> cars) {
        RaceCarRunnable winner = cars.get(0);
        for (RaceCarRunnable car : cars) {
            if (car.getFinishTime() < winner.getFinishTime()) {
                winner = car;
            }
        }
        return winner;
    }

    public static String convertToTime(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date(time));
    }

}