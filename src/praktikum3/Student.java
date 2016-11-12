package praktikum3;

import java.util.Collections;

/**
 * Created by talal on 02.11.16.
 */
public class Student extends Thread {

    private KassenBuffer<Kasse> kassenBuffer;

    public Student(String name, KassenBuffer<Kasse> kassenBuffer) {
        setName(name);
        setDaemon(true);
        this.kassenBuffer = kassenBuffer;
    }

    public void bezahlProzess() throws InterruptedException {

        Kasse k = null;
        /****************************** Anstellen **************************************/
        // Sortieren
        if (kassenBuffer.warteschlangeVorhanden()) {
            // Student bekommt die Information, welche Kasse mit der kleinsten Warteschlange..
            Collections.sort(kassenBuffer, (o1, o2) -> {
                int size1 = o1.getStudents().size();
                int size2 = o2.getStudents().size();
                return Integer.compare(size1, size2);
            });

            k = kassenBuffer.getFirst();
            // Student stellt sich an der Kasse mit der kleinsten Warteschlange an.
            k.anstellen(this);

            System.err.println("Warteschlange "+kassenBuffer);
            // Student besitzt die Kasse um dort zu bezahlen.
            kassenBuffer.benutzen(k);
            System.err.println(getName() + " benutzt die " + k.getName() + "...");
        }

        // keine Sortierung
        else {
            //k = kassenBuffer.getFirst();
            // Student besitzt die Kasse um dort zu bezahlen.
            System.err.println("Keine Warteschlange "+kassenBuffer);
            k = kassenBuffer.benutzen(null);
            k.anstellen(this);
            System.err.println(getName() + " benutzt die " + k.getName() + "...");
        }

        /*************************** Bezahlen ******************************/
        // Wenn Student an der Reihe ist, bezahlt er und gibt die Kasse frei.
        if (!k.getStudents().isEmpty() && k.getStudents().peek().equals(this)) {
            k.bezahlen();
            kassenBuffer.freigeben(k);
            System.err.println(getName() + " hat an der " + k.getName() + " bezahlt und hat sie freigegeben.");
        } else {
                System.err.println(getName() + " wartet noch...");
        }
    }

    //
    public void run() {
        while(!isInterrupted()) {
            try {
                bezahlProzess();
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.err.println(kassenBuffer);
    }

}
