import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class State {
	int id;
	HashMap<Character, ArrayList<Integer>> links; // letter, id of desired state

	public State(Integer i) {
		id = i;
		links = new HashMap<Character, ArrayList<Integer>>();
	}
}

public class salo_complement {
	public static void main(String[] args) throws IOException {
		Set<Character> alphabet = new HashSet<Character>();
		int alphabet_size = 0;
		Set<Integer> id_states;
		Set<Integer> final_states_id;
		Set<Integer> trans_states_id;
		ArrayList<State> states;

		// read input
		Scanner sc = new Scanner(new BufferedReader(new FileReader(args[0])));
		// Scanner sc = new Scanner(new BufferedReader(new
		// FileReader("in.txt")));
		ArrayList<String> input = new ArrayList<String>();
		while (sc.hasNext())
			input.add(sc.next());
		sc.close();

		// find all states
		id_states = new HashSet<Integer>();
		for (String s : input)
			if (s.length() == 1)
				try {
					int i = Integer.parseInt(s);
					id_states.add(i);
				} catch (NumberFormatException e) {
					alphabet.add(s.charAt(0)); // fill Alphabet
				}

		// create dummy states
		ArrayList<Integer> ids = new ArrayList<Integer>(id_states);
		states = new ArrayList<State>();
		for (int i = 0; i < id_states.size(); i++) {
			states.add(new State(ids.get(i)));
		}

		// file'em properly
		// sc = new Scanner(new BufferedReader(new FileReader("in.txt")));
		final_states_id = new HashSet<Integer>();
		int trans_counter = 0;
		for (int i = 0; i < input.size(); i = i + 4) {
			String state = input.get(i);
			if (state.equals("trans")) {
				trans_counter++;
				int id = Integer.parseInt(input.get(i + 1));
				char command = input.get(i + 2).charAt(0);
				int desired_id = Integer.parseInt(input.get(i + 3));
				ArrayList<Integer> newvalues = states.get(id).links
						.get(command);
				if (newvalues != null) {
					newvalues.add(desired_id);
					states.get(id).links.put(command, newvalues);
				} else {
					newvalues = new ArrayList<Integer>();
					newvalues.add(desired_id);
					states.get(id).links.put(command, newvalues);
				}
			}
			if (state.equals("final"))
				final_states_id.add(Integer.parseInt(input.get(i + 1)));
		}

		// take care of sink state
		alphabet_size = alphabet.size();
		if (trans_counter / ids.size() != alphabet_size) {
			// add sink state
			Collections.sort(ids); // find max id
			int sink_id = ids.get(ids.size() - 1) + 1;
			id_states.add(sink_id);
			states.add(new State(sink_id));

			// fill sink state transitions
			for (int i = 0; i < states.size(); i++) {
				State state = states.get(i);
				if (state.links.size() != alphabet_size)
					for (char ch : alphabet) {
						if (state.links.get(ch) == null) {
							ArrayList<Integer> newvalues = new ArrayList<Integer>();
							newvalues.add(sink_id);
							state.links.put(ch, newvalues);
						}
					}
			}
		}

		// printNFA(states, alphabet, final_states_id);

		// create trans states id
		trans_states_id = new HashSet<Integer>(id_states);
		trans_states_id.removeAll(final_states_id);

		// create ids for a new machine
		Set<Integer> M_final_states_id = new HashSet<Integer>(trans_states_id);
		Set<Integer> M_trans_states_id = new HashSet<Integer>(final_states_id);

		// simulate(states, M_final_states_id);
		printDFAHamerlyFormat(states, alphabet, M_final_states_id);
	}

	static void printNFA(ArrayList<State> states, Set<Character> alphabet,
			Set<Integer> final_states_id) {
		// print the NFA
		for (int i = 0; i < states.size(); i++)
			if (final_states_id.contains(states.get(i).id))
				System.out.println("Final " + states.get(i).id + ": "
						+ states.get(i).links.toString());
			else
				System.out.println("Trans " + states.get(i).id + ": "
						+ states.get(i).links.toString());
		System.out.println(alphabet);
	}

	static void simulate(ArrayList<State> states, Set<Integer> final_states_id) {
		// simulate
		 String command = "cc";		
		/*Scanner sc = new Scanner(System.in);
		String command = null;
		if (sc.hasNext())
			command = sc.next();
		else
			command = "s";
		sc.close();*/

		char[] cmd = command.toCharArray();
		Set<Integer> cur_sates = new HashSet<Integer>();
		cur_sates.add(0);
		Set<Integer> new_states;

		for (char ch : cmd) {
			new_states = new HashSet<Integer>();
			for (int st : cur_sates) {
				ArrayList<Integer> li = states.get(st).links.get(ch);
				if (li != null)
					new_states.addAll(li);
			}
			cur_sates = new_states;
		}
		// System.out.println(cur_sates);
		cur_sates.retainAll(final_states_id);
		if (cur_sates.size() > 0)
			System.out.println("[accept]");
		else
			System.out.println("[reject]");
	}

	static void printDFAHamerlyFormat(ArrayList<State> states,
			Set<Character> alphabet, Set<Integer> final_states_id) {
		// print the NFA
		State state = null;
		for (int i = 0; i < states.size(); i++) {
			state = states.get(i);
			for (char ch : alphabet)
				System.out.println("trans " + state.id + " " + ch + " "
						+ state.links.get(ch).get(0));
		}

		// mark final states
		for (int i : final_states_id)
			System.out.println("final " + i);

	}

}
