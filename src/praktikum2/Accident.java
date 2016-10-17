package praktikum2;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 16.10.16.
 */
public class Accident extends Thread {

    public List<Car> cars;

    public Accident(List<Car> cars) {
        this.cars = cars;
    }
    /**
     * Generiet eine Randomzeit
     * link="http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range"
     */
    public Long generateRT() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return ThreadLocalRandom.current().nextLong(0, 500);
    }

    public void run() {
        Long accidentTime = generateRT();
        try {
            sleep(accidentTime);
            makeAccident();
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }

    public void makeAccident() throws InterruptedException {
        for(Car car : cars) {
            if(car.isAlive()) {
                car.interrupt();
            }
        }
        throw new InterruptedException();
    }
}
