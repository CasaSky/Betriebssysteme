package praktikum3;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by talal on 02.11.16.
 */
public class Kasse extends Thread implements Comparable<Kasse>{
    private KassenBuffer<Kasse> kassenBuffer;
    private Queue<Student> students; //Warteschlange von Studenten

    public Queue<Student> getStudents() {
        return students;
    }


    public Kasse(String name, KassenBuffer<Kasse> kassenBuffer) {
        setName(name);
        this.kassenBuffer = kassenBuffer;
        this.students = new LinkedList<>();
    }

    // Student in der Warteschlange anstellen
    public void addStudent(Student s) {
        this.students.add(s);
    }

    public Student bezahlen() {
        Student s = this.students.poll(); // Student bezahlt und geht essen
        s.setStehtAn(false);
        return s;
    }

    public void run() {
        while(!isInterrupted()) {
            try {
                if (!kassenBuffer.getReentrantLock().isLocked() && kassenBuffer.getBuffer().size()!=0) {

                    if (this.getStudents().isEmpty()) {
                        if (kassenBuffer.getBuffer().contains(this)) {
                            kassenBuffer.quit(this);
                            System.err.println(getName() + " is geschlossen!");
                        }
                    }
                    else {
                        Student s = bezahlen();
                        System.err.println(s.getName()+" hat bezahlt und verlaesst die Kasse "+getName());
                    }
                    // kassenBuffer.enter(this);
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    public KassenBuffer<Kasse> getkassenBuffer() {
        return kassenBuffer;
    }

    @Override
    public int compareTo(Kasse o) {
        int size1 = students.size();
        int size2 = o.getStudents().size();
        return Integer.compare(size1, size2);
    }

    @Override
    public String toString() {
        return getName();
    }
}
