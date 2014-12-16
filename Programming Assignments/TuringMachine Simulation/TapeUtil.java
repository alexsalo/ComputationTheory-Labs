//credit to Dr Greg Hamerly
//http://cs.ecs.baylor.edu/~hamerly/courses/4336_14f/assignments/assignment_04.pdf

public class TapeUtil {
    public static final int BEGIN_SYM = 1;

    // A utility symbol used by these functions.  If you use this elsewhere in
    // the tape, these functions may not work.
    public static final int UTIL_SYM = -9999;

    //
    // Shift all characters to the right (starting at the current location and
    // ending at either the end of tape)
    // Put the given symbol at the starting position.
    // Leave the tape head on the cell immediately to the right of the new marker.
    //
    public static void shiftAndInsert(Tape t, int sym) {
        // Snapshot the current symbol and replace it with the util marker.
        int val = t.get();
        t.put(UTIL_SYM);

        // Keep moving to the right and shifting down until we see the end
        // of tape.
        while (val != Tape.EMPTY_SYM) {
            t.right();

            int val2 = t.get();
            t.put(val);
            val = val2;
        }

        // Rewind back to the start and write the given symbol in its place.
        while (t.get() != UTIL_SYM)
            t.left();
        t.put(sym);

        // Leave the tape head pointing to the same symbol
        t.right();
    }

    //
    // Find an occurrence of the given symbol at the current position or to the
    // right.  Reject if we can't find it.
    public static void findRight(Tape t, int sym) {
        while (t.get() != sym) {
            if (t.get() == Tape.EMPTY_SYM)
                Tape.reject();

            t.right();
        }
    }

    //
    // Find an occurrence of the given symbol at the current position or to the
    // left.  Reject if we can't find it.
    public static void findLeft(Tape t, int sym) {
        while (t.get() != sym)
            t.left();
    }

    //
    // Shift the whole tape to the right and insert a special, BEGIN_SYM
    // at the start.
    public static void insertBegin(Tape t) {
        shiftAndInsert(t, BEGIN_SYM);
    }

    //
    // Rewind back to the start of the tape.
    public static void rewind(Tape t) {
        findLeft(t, BEGIN_SYM);
        t.right();
    }
}
