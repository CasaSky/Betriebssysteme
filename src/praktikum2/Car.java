package praktikum2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 16.10.16.
 */
public class Car extends Thread implements Comparable<Car> {
    public final int MINTIME = 0;
    public final int MAXTIME = 99;
    public long startTime;
    public long driveTime;
    public int raceRounds;
    public int roundsDone = 0;

    public Car(int number, int raceRounds) {
        this.setName("Wagen "+number);
        this.raceRounds = raceRounds;
    }

    public Double getRunTime() {
        return driveTime / 1000000.0;
    }

    // Nach jeder gelaufene Runde wird fuer eine random Zeit das Auto angehalten
    public void run() {
        startTime = System.nanoTime();
        drive();
    }

    public void drive() {
        for(; roundsDone <= raceRounds && !isInterrupted(); roundsDone++){

            int roundTime = generateRRT();

            try {
                sleep(roundTime);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
        driveTime = System.nanoTime() - startTime;
    }

    @Override
    public int compareTo(Car o) {
        return this.getRunTime().compareTo(o.getRunTime());
    }

    public String toString() {
        return this.getName() + " " + "Zeit: " + getRunTime().intValue() + "\n";
    }

    /**
     * Generiet eine Randomzeit fuer eine Runde
     * link="http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range"
     */
    public int generateRRT() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return ThreadLocalRandom.current().nextInt(MINTIME, MAXTIME + 1);
    }
}
