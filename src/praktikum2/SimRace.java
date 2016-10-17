package praktikum2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by talal on 16.10.16.
 * Erstellt einen Rennen fuer eine gegebene Anzahl von Autos und Runden
 * Beendet, falls alle Autos im Ziel sind
 */
public class SimRace extends Thread {

    public int numberOfCars;
    public int raceRounds;

    public Accident accident;
    public List<Car> cars = new ArrayList<>(); // Liste der Autos

    public SimRace(int numberOfCars, int raceRounds) {
        this.numberOfCars = numberOfCars;
        this.raceRounds = raceRounds;
    }

    // Erzeugt, startet, stoppt(bis zur nächsten Runde) die Autos
    public void run() {

        // Erzeugt die Autos
        for (int i = 1; i <= numberOfCars; i++) {
            Car car = new Car(i, raceRounds);
            cars.add(car);
        }

        accident = new Accident(cars);
        accident.start();

        for (Car car : cars) {
            car.start();
        }

        for (Car car : cars) {
            try {
                car.join();
            } catch (InterruptedException e) {

            }
        }

        // Alle sind haben das Rennen beendet
        accident.interrupt();
        if(accident.isAlive()) {
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

    public static void main(String[] args) {
        final int NUMBEROFCARS = 5;
        final int RACEROUNDS = 3;

        SimRace simRace = new SimRace(NUMBEROFCARS, RACEROUNDS);
        simRace.start();

        //Auf das Ende des Rennen warten
        try {
            simRace.join();
        } catch (InterruptedException e) {
        }

    }
}
