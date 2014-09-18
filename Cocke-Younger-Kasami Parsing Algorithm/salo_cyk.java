import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class salo_cyk {
	static HashMap<Character, ArrayList<String>> cfg;
	static String command;
	static int N; // command length
	static ArrayList<ArrayList<HashSet<Character>>> T = 
			new ArrayList<ArrayList<HashSet<Character>>>(); // table for cyk																										
	static ArrayList<String> inputStrings; // to read cfg file

	public static void main(String[] args) throws FileNotFoundException {
		readCFGdesc(args[0]);
		initCFG();
		readCommand();
		
		if (!command.isEmpty()) {
			simulateCFG();
			acceptReject();
		} else
			System.out.println("reject");
		// System.out.println(cfg); printT();
	}

	static void readCommand(){
		Scanner sc = new Scanner(System.in);
		if (sc.hasNext())
			command = sc.next();
		else
			command = "";
		sc.close();
		//command = "aacbb"; //test
		N = command.length();
	}
	
	static void readCFGdesc(String arg) throws FileNotFoundException {
		//read input
		Scanner sc = new Scanner(new BufferedReader(new	FileReader(arg)));
		//Scanner sc = new Scanner(new BufferedReader(new FileReader("in.txt")));
		inputStrings = new ArrayList<String>();
		while (sc.hasNext())
			inputStrings.add(sc.nextLine());
		sc.close();
	}

	static void initCFG() {
		cfg = new HashMap<Character, ArrayList<String>>();
		for (String s : inputStrings) {
			Scanner sc = new Scanner(s);
			char ch = sc.next().charAt(0);
			sc.next(); // -> sign
			sc.useDelimiter("[|\\s]");
			ArrayList<String> newvalues = new ArrayList<String>();
			while (sc.hasNext())
				newvalues.add(sc.next());
			cfg.put(ch, newvalues);
			sc.close();
		}
	}

	static HashSet<String> crossProd(HashSet<Character> set1,
			HashSet<Character> set2) {
		HashSet<String> crossProd = new HashSet<String>();
		for (char ch1 : set1)
			for (char ch2 : set2)
				crossProd.add((String.valueOf(ch1) + String.valueOf(ch2)));
		return crossProd;
	}

	static void printT() {
		for (int i = 0; i < T.size(); i++) {
			for (int j = 0; j < T.get(i).size(); j++)
				System.out.print(T.get(i).get(j));
			System.out.println();
		}
	}

	static HashSet<String> genCandidates(int i, int j) {
		HashSet<String> crossProd = new HashSet<String>();
		// all the combinations to collect the next row
		for (int a = 1; a <= i; a++)
			crossProd.addAll(crossProd(T.get(i - a).get(j),
					T.get(a - 1).get(i + j + 1 - a)));
		return (crossProd);
	}

	static void simulateCFG() {
		HashSet<String> candidates;
		for (int i = 0; i < N; i++) {
			ArrayList<HashSet<Character>> row = new ArrayList<HashSet<Character>>();
			for (int j = 0; j < N - i; j++) {
				candidates = new HashSet<String>();
				if (i == 0)
					candidates.add(command.substring(j, j + 1));
				else
					candidates = genCandidates(i, j);

				HashSet<Character> fromset = new HashSet<Character>();
				for (HashMap.Entry<Character, ArrayList<String>> entry : cfg
						.entrySet())
					for (String s : candidates)
						if (entry.getValue().contains(s))
							fromset.add(entry.getKey());

				row.add(j, fromset);
			}
			T.add(i, row);
		}
	}

	static void acceptReject() {
		// if has "S" at the top - then accept!
		if (T.get(T.size() - 1).get(0).contains("S".charAt(0)))
			System.out.println("accept");
		else
			System.out.println("reject");
	}
}
