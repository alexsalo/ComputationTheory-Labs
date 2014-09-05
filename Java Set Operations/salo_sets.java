/*
 * Author: Alex Salo
 * Assignment Title: Assignment 0
 * Assignment Description: This program outputs intersection, union and cross-product of two
 * sets obtained from input.txt
 * Due Date: 2/09/2014
 * Date Created: 8/29/2014
 * Date Last Modified: 8/29/2014
 * -----------------------------------
 * TASK TEXT
 * Write a C++ or Java program which takes as input two lists, turns them into sets, and computes and prints
their intersection, union, and cross-product (aka Cartesian product).
Your program should read from standard input. The input format for the program will be an integer n1,
followed by n1 whitespace-separated strings which make up the first list. This will be followed by an integer
n2, followed by n2 whitespace-separated strings that make up the second list. Here is an example:
3
apple pear
house
5
house
dog frog cat
house
For the output, print the intersection, then union, then cross-product. Sort the elements lexicographically
before printing them, and give each set element on its own line. Following Sipser, we will define lexicographic
sorting by word length, then alphabetically by ASCII value. Put a blank line after each printed set, including
the last. The output for this input is exactly:
intersection:
house
union:
cat
dog
frog
pear
apple
house
cross-product:
pear cat
pear dog
apple cat
apple dog
2house cat
house dog
pear frog
apple frog
house frog
pear house
apple house
house house
Your program should NOT produce any output other than exactly what is specified. For example, it should
not prompt the user for any input.
Remember that sets have only unique elements, so even if the list given contains two (or more) elements
that are the same, your program should only keep one of them.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class salo_sets {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// read file
		ArrayList<String> array = new ArrayList<String>();
		Scanner infile = null;

		infile = new Scanner(System.in);

		while (infile.hasNext()) {
			array.add(infile.next());
		}
		infile.close();
		// learn separation point
		int n = Integer.parseInt(array.get(0));

		// get to sets from initial array
		Set<String> set1 = new HashSet<String>(array.subList(1, n + 1));
		Set<String> set2 = new HashSet<String>(array.subList(n + 2,
				array.size()));

		// get intersection, onion and cross-product of sets
		Set<String> intersection = new HashSet<String>(set1);
		intersection.retainAll(set2);
		Set<String> union = new HashSet<String>(set1);
		union.addAll(set2);
		Set<String> crossProduct = crossProd(set1, set2);

		// print'em all!
		printSetLexiGraph("intersection", intersection);
		printSetLexiGraph("union", union);
		printSetLexiGraph("cross-product", crossProduct);

	}

	/**
	 * returns set with the cross product of two sets
	 */
	public static Set<String> crossProd(Set<String> s1, Set<String> s2) {
		Set<String> cp = new HashSet<String>();
		for (String s : s1)
			for (String ss : s2)
				cp.add(s + ' ' + ss);
		return cp;
	}

	/**
	 * sorts arraylists lexicographically
	 */
	public static ArrayList<String> sortLexiGraph(ArrayList<String> a) {
		Collections.sort(a);
		for (int j = 0; j < a.size(); j++)
			for (int i = 0; i < a.size() - 1; i++)
				if (a.get(i + 1).length() < a.get(i).length())
					Collections.swap(a, i, i + 1);
		return a;
	}

	/**
	 * prints set's elements on separate lines in lexicographical order. also
	 * prints name on the first line makes last line empty
	 */
	public static void printSetLexiGraph(String name, Set<String> set) {
		System.out.println(name + ":");
		for (String s : sortLexiGraph(new ArrayList<String>(set)))
			System.out.println(s);
		System.out.println();
	}

}
