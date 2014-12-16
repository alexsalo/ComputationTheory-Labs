// Using only 1 tape Turing Machine
import java.io.IOException;
import java.io.InputStreamReader;

public class salo_cube_singletape {
	static Tape input;
	static int pow = 3; // cube
	static char ch = 'a'; // desired symbol
	static char delim = '#'; // between logical tapes
	static char input_cursor = 10; // for input tape
	static char input_mark = 12; // to mark symbols ion input tape
	static char standart_cursor = 20;
	static char standart_mark = 22;

	public static void main(String[] args) {
		try {
			input = new Tape(new InputStreamReader(System.in));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TapeUtil.insertBegin(input);
		check1stCase();
		checkSymbols();
		prepareStandartTape();

		while (true) {
			moveInputRight();
			// input.debug();
			// if the end of input tape then halt and decide
			if (input.get() == delim) {
				// find the end of standart tape
				TapeUtil.findRight(input, standart_cursor);
				input.right();
				// if epmty sym next => perfect cube
				if (input.get() == Tape.EMPTY_SYM)
					Tape.accept();
				else
					Tape.reject();
			}
			// move standrt cursor right otherwise
			moveStandartRight();
			// input.debug();
		}
	}

	public static void moveStandartRight() {
		TapeUtil.findRight(input, standart_cursor);
		input.put(standart_mark);
		input.right();
		if (input.get() != Tape.EMPTY_SYM)
			input.put(standart_cursor);
		else
			fillStandart();
	}

	public static void moveInputRight() {
		TapeUtil.findLeft(input, input_cursor);
		input.put(input_mark);
		input.right();
		if (input.get() != delim)
			input.put(input_cursor);
	}

	// check 3^0 = 1
	static void check1stCase() {
		if (input.get() == ch) {
			input.right();
			if (input.get() == Tape.EMPTY_SYM)
				Tape.accept();
		}
	}

	// check if only ch in it
	public static void checkSymbols() {
		while (input.get() != Tape.EMPTY_SYM) {
			if (input.get() != ch)
				Tape.reject();
			input.right();
		}
	}

	static void prepareStandartTape() {
		input.put(delim);
		input.right();
		fillStandart();
		TapeUtil.rewind(input);
		input.put(input_cursor);
		// input.debug();
	}

	public static void fillStandart() {
		TapeUtil.findLeft(input, delim);
		input.right();
		input.put(standart_cursor);
		input.right();
		input.put(ch);
		input.right();
		input.put(ch);
		input.right();
	}
}
