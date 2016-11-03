package praktikum3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by talal on 02.11.16.
 */
public class Mensa {
    public static void main(String[] args) {
        KassenQueue<Kasse> kassenQueue = new KassenQueue<>();

        LinkedList<Kasse> kassen = new LinkedList<>();
        LinkedList<Student> studenten = new LinkedList<>();

        for (int i=0; i<3; i++) {
            Kasse k = new Kasse("Kasse "+(i+1),kassenQueue, studenten);
            kassen.add(k);
            k.start();
        }

        for (int i=0; i<10; i++) {
            Student s = new Student("Student "+(i+1),kassen,kassenQueue);
            studenten.add(s);
            s.start();
        }

        for (Kasse k : kassen) {
            try {
                k.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Student s : studenten) {
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Kasse k : kassen) {
            k.interrupt();
        }

        for (Student s : studenten) {
            s.interrupt();
        }

    }
}
