/*
 * Author: Alex Salo
 * Assignment Title: Assignment 0
 * Assignment Description: This program outputs intersection, union and cross-product of two
 * sets obtained from input.txt
 * Due Date: 2/09/2014
 * Date Created: 9/04/2014
 * Date Last Modified: 9/04/2014
 * -----------------------------------
 * TASK TEXT
 * Write a C++ or Java program that enumerates the set of all pairs of natural numbers (1, 2, . . . ). For this
program you should pretend the int type supports unbounded magnitude. This makes it easier to think
about our program enumerating an infinite set. Of course a consequence of this is that we’ll also pretend
that a loop like the following will never terminate (hint: this is important):
int i = 1;
while (i > 0)
i++;
To enumerate pairs, just print out lines like the following:
(1, 1)
(1, 2)
...
 */

public class salo_pairs {

	public static void main(String[] args) {
		int i = 1, j = 1, tmp = 1;
		while (true) {
			while (j <= tmp) {
				System.out.println("(" + i + ", " + j + ")");
				j++;
				i--;
			}
			j = 1;
			i = ++tmp;
		}
	}
}
