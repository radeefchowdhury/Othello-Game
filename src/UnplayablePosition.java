public class UnplayablePosition extends Position{

    public static final char UNPLAYABLE = '*';

    public boolean canPlay() {
        return false;
    }
}
