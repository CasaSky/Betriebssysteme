package praktikum2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 16.10.16.
 * Erstellt einen Rennen fuer eine gegebene Anzahl von Autos und Runden
 * Beendet, falls alle Autos im Ziel sind
 */
public class SimRace extends Thread {
    public final int MINTIME = 0;
    public final int MAXTIME = 99;
    public int numberOfCars;
    public int raceRounds;
    public int roundTime; // Zeit einer Runde in ms
    public boolean allFinish;

    public Accident accident = new Accident();
    public List<Car> cars = new ArrayList<>(); // Liste der Autos

    public SimRace(int numberOfCars, int raceRounds) {
        this.numberOfCars = numberOfCars;
        this.raceRounds = raceRounds;
    }

    // Erzeugt, startet, stoppt(bis zur nächsten Runde) die Autos
    public void run() {

             accident.start();
            // Erzeugt die Autos
            for (int i = 1; i <= numberOfCars; i++) {
                Car car = new Car(i);
                car.start();
                cars.add(car);
            }

            int i=0;
            // Ein Stop der Autos pro Runde
            while(!this.isInterrupted() && i<raceRounds) {
                roundTime = generateRRT();
                // Jetzt alle Autos fuer diese Zeit anhalten
                for (Car car : cars) {
                    try {
                        car.sleep(roundTime);
                        if (!accident.isAlive()) {
                            this.interrupt();
                        }
                    } catch (InterruptedException e) {
                        car.interrupt(); // Auto anhalten
                        //this.interrupt();
                        System.err.println("Anhalten von "+car.getName()+" wegen eines Unfalls!");
                    }
                }
                i++;
            }

            // Nichts tun falls ein Unfall passiert ist
            if (!this.isInterrupted()) {
                // Ende des Rennen - Alle Autos anhalten(Ziel erreicht)
                for (Car car : cars) {
                    car.interrupt();
                }

                // Warten bis alle Autos am Ziel sind, anschliessend sortieren und Ergebnis anzeigen
                waitForFinishAndSort();
            }
    }

    public void waitForFinishAndSort() {
        // Warten auf das Ende aller Autos und merke das Rennen mit beendet
        for (Car car : cars) {
            if (car.isAlive()) {
                try {
                    car.join();
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
            allFinish = true;
        }

        // Platziere die Autos, falls alle beendet sind
        if (allFinish) {
            sortCars();
        }
    }

    /**
     * Sortiert die Autos und zeigt das Ergebnis an
     */
    public void sortCars() {
        Collections.sort(cars);
        System.err.println(toString());

    }

    // Ausgabe
    public String toString() {
        String result = "**** Endstand ****\n";
        for (Car car : cars) {
            result+=(cars.indexOf(car)+1)+". Platz: "+car.toString();
        }
        return result;
        //return cars.toString();
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


    public static void main(String[] args) {
        final int NUMBEROFCARS = 5;
        final int RACEROUNDS = 3;

        SimRace simRace = new SimRace(NUMBEROFCARS, RACEROUNDS);
        simRace.start();

        //Auf das Ende des Rennen warten
        try {
            simRace.join();
        } catch (InterruptedException e) {
            simRace.interrupt();
        }

    }
}
