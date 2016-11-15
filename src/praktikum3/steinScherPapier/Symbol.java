package praktikum3.steinScherPapier;

/**
 * Created by talal on 13.11.16.
 */
public enum Symbol {
    SCHERE(0),
    STEIN(1),
    PAPIER(2);

    private final Integer index;

    Symbol(Integer index) {
        this.index = index;
    }

    public Integer index() {
        return index;
    }
}
