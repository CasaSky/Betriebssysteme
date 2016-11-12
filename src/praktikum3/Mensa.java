package praktikum3;

import java.util.LinkedList;

/**
 * Created by talal on 02.11.16.
 */
public class Mensa extends Thread{
    KassenBuffer<Kasse> kassenBuffer = new KassenBuffer<>();
    LinkedList<Kasse> kassenForBuffer = new LinkedList<>();
    LinkedList<Student> studenten = new LinkedList<>();

    int counter;
    int schliessZeit;
    int anzahlKassen, anzahlStudents;

    public Mensa(int schliessZeit, int anzahlKassen, int anzahltStudents) {
        this.schliessZeit = schliessZeit;
        this.anzahlKassen = anzahlKassen;
        this.anzahlStudents = anzahltStudents;
    }

    public void run() {
        createKassen();
        createStudents();

        while (!isInterrupted()) {
                if (counter != schliessZeit) {
                    counter++;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    System.err.println("----------------------------->Mensa ist geschlossen!");
                    interrupt();
                }
        }
    }

    public void createStudents() {
        for (int i=0; i<anzahlStudents; i++) {
            Student s = new Student("Student "+(i+1), kassenBuffer);
            s.start();
            studenten.add(s);
        }
    }

    public void createKassen() {
        for (int i=0; i<anzahlKassen; i++) {
            Kasse k = new Kasse("Kasse "+(i+1));
            kassenForBuffer.add(k);
        }
        kassenBuffer = new KassenBuffer<>(kassenForBuffer);
    }

    public void joinAll() throws InterruptedException {
        super.join();

        for (Student s : studenten) {
            try {
                s.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void interruptAll() {
        super.interrupt();

        for (Student s : studenten) {
            if (!s.isInterrupted()) {
                s.interrupt();
            }
        }
    }

    public LinkedList<Student> getStudenten() {
        return studenten;
    }

    public static void main(String[] args) {

        Mensa mensa = new Mensa(2, 3, 10);
        mensa.start();
    }
}
