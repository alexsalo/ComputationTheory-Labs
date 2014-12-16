/*To help us understand the capabilities of the Turing Machine model, we will write a program in a restricted
form of Java that is not too different from a Turing machine. The file Tape.java will help us to think about
how a Turing machine can use it’s tape like a computer’s memory. You can access it here:
• http://cs.baylor.edu/~hamerly/courses/4336_14f/assignments/Tape.java
1We’re going to deliberately avoid features of Java so that our computation model is essentially the same
as a finite automaton with a finite number of tapes. You can use Tape objects, but you can only use a
fixed number of them. You are not permitted to use any arithmetic (+, -, ++, etc.), bitwise (^, |, &, etc.),
bit-shifting (<<, >>, etc.) or inequality (>, <, etc.) operators. The only types you will need to use are
char, int and boolean. You also can’t use recursion or dynamic memory allocation aside from allocating the
Tape (you will observe that the Tape class uses dynamic memory allocation and arithmetic operators in its
implementation; you just can’t use these programming language features in your code). You can call the
special method accept() to accept, and use reject() to reject.
Observe that the Tape class has a constructor that will read its input from a Java Reader (which is in turn
reading from standard input). This will let you read a whole line of input without using arrays, strings
or arithmetic. Since we can’t use strings or arrays, you will want to initially read the input using this
constructor.
Using this restricted model of computation, write a program that accepts strings of the form a
i where i is a
perfect cube (it should reject all other inputs). The program you submit should not print anything except
through the methods accept() and reject(). For a little bit of extra credit, construct your program so it
only uses one tape.
Have a look at TapeUtil.java for a description of how you might write subroutines that make some things
easier when working with tapes: http://cs.baylor.edu/~hamerly/courses/4336_14f/assignments/TapeUtil.java.
You may write any code you want (including extra methods) within these guidelines.
 * 
 * 
 * Using only 1 tape Turing Machine
 * */
import java.io.IOException;
import java.io.InputStreamReader;


public class salo_cube {
	static Tape input;
	static Tape standart = new Tape();
	static int pow = 3; //cube
	static char ch = 'a'; //desired char
	
	public static void main(String[] args) {
		fillStandart();
		try {
			input = new Tape(new InputStreamReader(System.in));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//check 3^0 = 1 case
		if (input.get() == ch){
			moveInputRight();
			if (input.get() == Tape.EMPTY_SYM)
				Tape.accept();
			input.left();				
		}	
		else Tape.reject();
		
		while(true){		
			if (input.get() == Tape.EMPTY_SYM)
				if (standart.get() == Tape.EMPTY_SYM)
					Tape.accept();
				else					
					Tape.reject();
			else
				if (standart.get() == Tape.EMPTY_SYM)
					fillStandart();
			
			moveInputRight();
			standart.right();
		}
	}
	
	public static void moveInputRight(){
		if (input.get() == ch)
			input.right();
		else
			Tape.reject();
	}
	
	public static void fillStandart(){
		standart.put(ch);
		standart.right();
		standart.put(ch);
		standart.right();
		standart.put(ch);
		
		standart.left();
		standart.left();
	}
}
