/*
 * Author: Alex Salo
 * Assignment Title: Assignment 1
 * Assignment Description: This program simulates NFA
 * Due Date: 9/09/2014
 * Date Created: 9/4/2014
 * Date Last Modified: 9/5/2014
 * -----------------------------
 * TASK TEXT
 * For this problem, write a program that simulates NFAs. It will read an NFA description in the format
given below. It will accept (print out [accept]) if and only if a given input string is in the language of its
given NFA. Otherwise, your program will print out [reject]. It should not print anything but one of these
messages.
Your program will read its NFA description from a file and will read the input string for that NFA from
standard input. The filename containing the NFA description will be given as the first (and only) command
line parameter. Below is a sample NFA description for your program. Each line starting with trans describes
a transition. The first token after trans is an integer which is the state the transition is from, the next
token is a single printable character which is the input symbol, and the third token is an integer which is
the destination state.
Input symbols will always be in the range a. . . z. Since this describes an NFA, there may be more than one
transition from the same state on the same input symbol (but we will not simulate ε transitions). Each
line that starts with the word final indicates a final state. There may be multiple ‘final’ lines. All final
lines will come after all trans lines. Undefined transitions lead to ‘sink states’, which might not be explicitly
described. State 0 is always the start state.
trans 0 a 0
trans 0 b 0
trans 0 c 0
trans 0 a 1
trans 1 b 2
trans 2 c 3
trans 0 b 4
trans 4 b 5
trans 5 b 3
trans 3 a 3
trans 3 b 3
trans 3 b 3
final 3
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

public class salo_nfa {
	public static void main(String[] args) throws IOException {
		// read input
		Scanner sc = new Scanner(new BufferedReader(new FileReader(args[0])));
		ArrayList<String> input = new ArrayList<String>();
		while (sc.hasNext())
			input.add(sc.next());
		sc.close();

		// find all states
		Set<Integer> id_states = new HashSet<Integer>();
		for (String s : input)
			try {
				int i = Integer.parseInt(s);
				id_states.add(i);
			} catch (NumberFormatException e) {
			}

		// create dummy states
		ArrayList<Integer> ids = new ArrayList<Integer>(id_states);
		ArrayList<State> states = new ArrayList<State>();
		for (int i = 0; i < id_states.size(); i++) {
			states.add(new State(ids.get(i)));
		}

		// file'em properly
		// sc = new Scanner(new BufferedReader(new FileReader("in.txt")));
		Set<Integer> final_states_id = new HashSet<Integer>();
		for (int i = 0; i < input.size(); i = i + 4) {
			String state = input.get(i);
			if (state.equals("trans")) {
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

		// print the NFA
		/*
		 * for (int i = 0; i < states.size(); i++) if
		 * (final_states_id.contains(states.get(i))) System.out.println("Final "
		 * + states.get(i).id + ": " + states.get(i).links.toString()); else
		 * System.out.println("Trans " + states.get(i).id + ": " +
		 * states.get(i).links.toString()); sc.close();
		 */

		// simulate
		// String command = "abbcabab";
		sc = new Scanner(System.in);
		String command = sc.next();
		sc.close();
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
}
