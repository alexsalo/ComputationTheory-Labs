//credit to Dr Greg Hamerly
//http://cs.ecs.baylor.edu/~hamerly/courses/4336_14f/assignments/assignment_04.pdf

import java.io.Reader;
import java.util.Vector;
import java.io.BufferedReader;

public class Tape {
    // Special symbol marking an empty spot on the tape.
    // You can read this symbol, but you can't write it.
    public static final int EMPTY_SYM = -1;

    // Current position on the tape.
    private int position = 0;

    // Contents of the tape.
    private Vector<Integer> contents = new Vector<Integer>();

    // Make a new, empty tape.
    public Tape() {
        contents.add(EMPTY_SYM);
    }

    // Make a new tape that contains a single line of input from the given
    // input stream.
    public Tape(Reader input) throws java.io.IOException {
        // Read a line of input and put it on the tape.
        String line = (new BufferedReader(input)).readLine();
        for (int i = 0; i < line.length(); ++i) {
            contents.add((int)line.charAt(i));
        }

        // Make sure we have at least one symbol on the tape.
        if (contents.size() == 0)
            contents.add(EMPTY_SYM);
    }

    // Move left on the tape.
    public void left() {
        // Make sure we don't move off the left end of the tape.
        if (position == 0)
            reject("left move at start of tape");
        position--;
    }

    // Move right on the tape.
    public void right() {
        position++;

        // Grow the tape if we run out of space.
        if (position == contents.size())
            contents.add(EMPTY_SYM);
    }

    // Read the current symbol on the tape.
    // Returns -1 (EMPTY_SYM) if nothing has been put in this cell.
    public int get() {
        return contents.get(position);
    }

    // Write a new symbol to the tape.  This will reject if you try to
    // write the EMPTY_SYM
    public void put(int val) {
        // don't allow the EMPTY_SYM to be written
        if (val == EMPTY_SYM) {
            reject("can't write EMPTY_SYM");
        }
        contents.set(position, new Integer(val));
    }

    // Enumerate the string starting at the current position on the tape up to
    // a value of 0.  This is for enumerating languages.
    public void enumerate() {
        // Walk from the current position to a delimiter
        for (int i = position; i < contents.size(); i++) {
            int val = contents.get(i);
            if (val == 0 || val == EMPTY_SYM) {
                break;
            }
            // Print out any printable character.
            if ((val >= 32 && val < 127) || (val == '\n')) {
                System.out.print((char)val);
            }
        }
        // Put a newline at the end.
        System.out.println();
    }

    // This is just a debugging function to show the contents of the tape.
    // It's not officially part of the operation of the tape but you can use
    // it in writing your programs.
    public void debug() {
        // Print a line at the top and the bottom.
        System.out.println("---------------");
        for (int pos = 0; pos < contents.size(); pos++){
            // Mark the current cell
            if (pos == position)
                System.out.print("-> ");
            else
                System.out.print("   ");

            // Print the contents as an integer and a character (if appropriate)
            int val = contents.get(pos);
            System.out.print(" " + val);
            if (val >= 32 && val < 127)
                System.out.print(" '" + (char)val + "'");
            System.out.println();
        }
        System.out.println("---------------");
    }

    // Accept.
    public static void accept() {
        System.out.println("[accept]");
        System.exit(0);
    }

    // Reject.
    public static void reject() {
        System.out.println("[reject]");
        System.exit(0);
    }

    // Reject and print out a message why.
    public static void reject(String message) {
        System.out.println("[reject (" + message + ")]");
        System.exit(0);
    }
}