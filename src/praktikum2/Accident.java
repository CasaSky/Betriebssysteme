package praktikum2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by talal on 16.10.16.
 */
public class Accident extends Thread {
    /**
     * Generiet eine Randomzeit
     * link="http://stackoverflow.com/questions/363681/generating-random-integers-in-a-specific-range"
     */
    public Long generateRT() {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return ThreadLocalRandom.current().nextLong();
    }

    public void run() {
        Long startTime = System.nanoTime();
        Long accidentTime = generateRT();
        Long nowTime;
        do {
            nowTime = System.nanoTime();
        } while(nowTime - startTime < accidentTime);
    }
}
