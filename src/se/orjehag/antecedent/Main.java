package se.orjehag.antecedent;

/**
 * Created by erik on 19/03/16.
 */
public final class Main {
    private Main() {}

    // The main method needs to exits even though it's
    // never used by me because its the entry point of
    // the program. The return value of SimFrame is never
    // used but I cannot remove the variable because then
    // I get another warning telling me that the return
    // value of SimFrame is never stored.
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        //noinspection UnusedAssignment
        SimFrame frame = new SimFrame();
    }
}
