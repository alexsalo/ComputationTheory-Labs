import java.io.IOException;
import java.io.InputStreamReader;


public class salo_cube {
	static Tape input;
	static Tape curnum = new Tape();
	static Tape a = new Tape();
	static Tape b = new Tape();
	static Tape c = new Tape();
	static Tape result = new Tape();
	static char SYM = 'a';
	static char CNT_SYM = '1';
	
	public static void main(String[] args) {
		try {
			input = new Tape(new InputStreamReader(System.in));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//reject emptystring
		if (input.get() == Tape.EMPTY_SYM)
			Tape.reject();
		
		TapeUtil.insertBegin(input);
		TapeUtil.insertBegin(curnum);
		TapeUtil.insertBegin(result);	
		
		//check that input doesn't contain any non-'a' symbols
		while (input.get() != Tape.EMPTY_SYM){
			if (input.get() != SYM)
				Tape.reject();
			input.right();
		}
		TapeUtil.rewind(input);
			
		curnumPlusPlus();
		getCube();

		while(true){							
			input.right();
			result.right();
			if (input.get() == Tape.EMPTY_SYM && result.get() != Tape.EMPTY_SYM)
				Tape.reject();
			if (result.get() == Tape.EMPTY_SYM)
				if (input.get() == Tape.EMPTY_SYM)
					Tape.accept();
				else{
					TapeUtil.rewind(result);
					curnumPlusPlus();	
					getCube();
					TapeUtil.rewind(input);
				}
		}	
	}
	
	static void curnumPlusPlus(){
		while (curnum.get() != Tape.EMPTY_SYM)
			curnum.right();
		curnum.put(CNT_SYM);
		curnum.right();
		TapeUtil.rewind(curnum);
	}
	
	static void getCube(){
		fillABC();
		while (a.get() != Tape.EMPTY_SYM){
			while (b.get() != Tape.EMPTY_SYM){
				while (c.get() != Tape.EMPTY_SYM){
					result.put(CNT_SYM);
					result.right();
					c.right();
				}
				b.right();
				TapeUtil.rewind(c);
			}
			a.right();
			TapeUtil.rewind(b);
			TapeUtil.rewind(c);
		}
		TapeUtil.rewind(result);
	}
	
	static void fillABC(){
		TapeUtil.insertBegin(a);
		TapeUtil.insertBegin(b);
		TapeUtil.insertBegin(c);
		while (curnum.get() != Tape.EMPTY_SYM){
			a.put(curnum.get());
			b.put(curnum.get());
			c.put(curnum.get());
			a.right();
			b.right();
			c.right();
			curnum.right();
		}		
		TapeUtil.rewind(a);
		TapeUtil.rewind(b);
		TapeUtil.rewind(c);
	}
}
