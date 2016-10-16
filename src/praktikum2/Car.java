package praktikum2;

import java.util.Comparator;

/**
 * Created by talal on 16.10.16.
 */
public class Car extends Thread implements Comparable<Car> {
    public long startTime;
    public long runTime;

    public Car(int number) {
        this.setName("Wagen "+number);
       startTime = System.nanoTime();
    }

    public Double getRunTime() {
        return runTime / 1000000.0;
    }

    // Solange der Thread laeuft, wird die Zeit weiter berechnet
    public void run() {
        while (!isInterrupted()) {
            runTime = System.nanoTime() - startTime;
        }
    }

    @Override
    public int compareTo(Car o) {
        return this.getRunTime().compareTo(o.getRunTime());
    }

    public String toString() {
        return this.getName() + " " + "Zeit: " + getRunTime().intValue() + "\n";
    }
}
