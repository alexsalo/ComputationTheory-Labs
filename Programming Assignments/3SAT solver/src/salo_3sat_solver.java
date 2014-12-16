import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class salo_3sat_solver {
	static ArrayList<String> inputStrings = new ArrayList<String>();
	static int sat_size;
	static String filename = "t3.txt";
	static ArrayList<Character> alphabet;
	static int alphabet_size;
	static int combinator;
	static String result = "[reject]";

	public static void main(String[] args) {
		if (readFile())
			if (isValidInput()) {
				makeAlphabet();
				System.out.println(alphabet);
				initCombinator();
				if (isSatisfiable())
					result = "[accept]";
			}
		System.out.println(result);
	}

	static boolean isSatisfiable() {
		boolean result = false;
		inputStrings.remove(0); // remove size value
		while (combinator-- > 0 && !result) {
			boolean attempt = true;
			int i = -1;
			while (++i < sat_size && attempt)
				attempt = checkClause(getClause(i), getBinary(combinator));
			result = attempt;
			System.out.println(Integer.toBinaryString(combinator));
			//System.out.println(combinator);
		}
		return result;
	}
	
	static String[] getClause(int n_th){
		String[] clause = new String[3];
		for (int j = 0; j < 3; j++)
			clause[j] = inputStrings.get(j + n_th * 3);
		return clause;
	}
	
	static boolean checkClause(String[] clause, boolean[] alphabet_values){
		boolean result = false;
		int j = -1;
		while (++j < 3 && !result){
			String s = clause[j];
			if (s.length() == 1 && alphabet_values[alphabet.indexOf(s.charAt(0))]
			|| s.length() == 2 && !alphabet_values[alphabet.indexOf(s.charAt(1))])
				result = true;
		}
		return result;
	}

	static boolean[] getBinary(int a) {
		boolean[] result = new boolean[alphabet_size];
		String s = Integer.toBinaryString(a);
		int size_dif = alphabet_size - s.length();
		for (int i = 0; i < s.length(); i++)
			if (s.charAt(i) == '1')
				result[size_dif + i] = true;
		return result;
	}

	static boolean readFile() {
		boolean result = true;
		// Scanner sc = new Scanner(new BufferedReader(new FileReader(arg)));
		Scanner sc = null;
		try {
			sc = new Scanner(new BufferedReader(new FileReader(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sc = new Scanner(System.in);
		while (sc.hasNext())
			inputStrings.add(sc.next());
		sc.close();
		if (inputStrings.size() == 0)
			result = false;
		return result;
	}

	static boolean isValidInput() {
		sat_size = Integer.parseInt(inputStrings.get(0));
		boolean result = true;
		if ((inputStrings.size() - 1) / 3.0 != sat_size)
			result = false;
		return result;
	}

	static void makeAlphabet() {
		HashSet<Character> set = new HashSet<Character>();
		for (String s : inputStrings) {
			for (int i = 0; i < s.length(); i++) {
				Character ch = s.charAt(i);
				if (Character.isLetter(ch))
					set.add(ch);
			}
		}
		alphabet = new ArrayList<Character>(set);
	}

	static void initCombinator() {
		alphabet_size = alphabet.size();
		combinator = (int) Math.pow(2, alphabet_size);
	}
}
