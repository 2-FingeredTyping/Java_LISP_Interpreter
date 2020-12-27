import java.io.IOException;
import java.util.List;

public class LispOperators {
	
	static public LispObject LDef(LList x) throws IOException {
		Atom a;
		LispObject b;
		if(x.size() != 2) {
			throw new IOException("Function 'define': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'define': " + x.getContents().get(0).printName() + " is not a symbol");
		}else {
			a = (Atom) x.getContents().get(0);
			b = x.getContents().get(1);
			Interpreter.getEnv().addVar(a.printName(), b);
			return b;
		}
	}
	
	static public LispObject LSet(LList x) throws IOException {
		Atom a;
		LispObject b;
		if(x.size() != 2) {
			throw new IOException("Function 'set!': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'set!': " + x.getContents().get(0).printName() + " is not a symbol");
		}else {
			a = (Atom) x.getContents().get(0);
			b = x.getContents().get(1);
			Interpreter.getEnv().setVar(a.printName(), b);
			return b;
		}
	}
	
	static public LispObject LFunc(LList x) throws IOException {
		Atom a;
		LList b;
		LispObject c;
		String[] args;
		if(x.size() != 3) {
			throw new IOException("Function 'defun': expected 3 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'defun!': " + x.getContents().get(0).printName() + " is not a symbol");
		}else if(!(x.getContents().get(1) instanceof LList)) {
				throw new IOException("Function 'defun': " + x.getContents().get(1).printName() + " is not a List");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (LList) x.getContents().get(1);
			c = x.getContents().get(2);
			args = new String[b.size()];
			
			for(int i = 0; i < b.size(); i++) {
				args[i] = b.getContents().get(i).printName();
			}
			Interpreter.getEnv().addFunc(new LispFunction(a.printName(), args, c.printName(), b.size()));
			return a;
		}
	}
	
	static public LispObject car(LList x) throws IOException {
		LList a;
		if(x.size() != 1) {
			throw new IOException("Function 'car': expected 1 argument, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof LList)) {
			throw new IOException("Function 'car': " + x.getContents().get(0).printName() + " is not a list");
		}else {
			a = (LList) x.getContents().get(0);
			if(a.size() >= 1) {
				return a.getContents().get(0);
			}else {
				return a;
			}
		}
	}
	
	static public LList cdr(LList x) throws IOException {
		LList a;
		if(x.size() != 1) {
			throw new IOException("Function 'cdr': expected 1 argument, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof LList)) {
			throw new IOException("Function 'cdr': " + x.getContents().get(0).printName() + " is not a list");
		}else {
			a = (LList) x.getContents().get(0);
			if(a.size() >= 1) {
				return new LList(a.getContents().subList
						(1, a.size()), a.isLiteral(), 0);
			}else {
				return a;
			}
		}
	}
	
	static public LList cons(LList x) throws IOException {
		LispObject a;
		LList b;
		if(x.size() != 2) {
			throw new IOException("Function 'cons': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(1) instanceof LList)) {
			throw new IOException("Function 'cons': " + x.getContents().get(1).printName() + " is not a List");
		}else {
			a = x.getContents().get(0);
			b = (LList) x.getContents().get(1);
			List<LispObject> tmp = b.getContents();
			tmp.add(0, a);
			return new LList(tmp, b.isLiteral(), 0);
		}
	}
	
	static public Atom LAdd(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '+': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '+': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '+': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '+': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '+': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			
			return new Atom(Integer.toString(a.getNum() + b.getNum()), true, 0);
		}
		
	}
	
	static public Atom LMult(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '*': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '*': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '*': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '*': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '*': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			return new Atom(Integer.toString(a.getNum() * b.getNum()), true, 0);
		}
		
	}
	
	static public Atom LSub(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '-': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '-': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '-': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '-': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '-': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			return new Atom(Integer.toString(a.getNum() - b.getNum()), true, 0);
		}
		
	}
	
	static public Atom LDiv(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '/': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '/': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '/': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '/': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '/': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			return new Atom(Integer.toString(a.getNum() + b.getNum()), true, 0);
		}
		
	}
	
	static public Atom LSqrt(LList x) throws IOException {
		Atom a;
		if(x.size() != 1) {
			throw new IOException("Function 'sqrt': expected 1 argument, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'sqrt': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function 'sqrt': " + x.getContents().get(0).printName() + " is not a number");
		}else {
			a = (Atom)x.getContents().get(0);
			return new Atom(Integer.toString((int)Math.sqrt(a.getNum())), true, 0);
		}
		
	}
	
	static public Atom LPow(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function 'pow': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'pow': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function 'pow': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function 'pow': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function 'pow': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			return new Atom(Integer.toString((int)Math.pow(a.getNum(), b.getNum())), true, 0);
		}
		
	}
	
	static public Atom LGr(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '>': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '>': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '>': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '>': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '>': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getNum() > b.getNum()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LLs(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '<': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '<': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '<': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '<': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '<': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getNum() < b.getNum()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LEq(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '=': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '=': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '=': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '=': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '=': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getNum() == b.getNum()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LNe(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function '!=': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function '!=': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function '!=': " + x.getContents().get(1).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(0)).isNum()) {
			throw new IOException("Function '!=': " + x.getContents().get(0).printName() + " is not a number");
		}else if(!((Atom)x.getContents().get(1)).isNum()) {
			throw new IOException("Function '!=': " + x.getContents().get(1).printName() + " is not a number");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getNum() != b.getNum()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LAnd(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function 'and': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'and': " + x.getContents().get(0).printName() + " is not a symbol");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function 'and': " + x.getContents().get(1).printName() + " is not a symbol");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getBool() && b.getBool()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LOr(LList x) throws IOException {
		Atom a;
		Atom b;
		if(x.size() != 2) {
			throw new IOException("Function 'and': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'and': " + x.getContents().get(0).printName() + " is not a symbol");
		}else if(!(x.getContents().get(1) instanceof Atom)) {
			throw new IOException("Function 'and': " + x.getContents().get(1).printName() + " is not a symbol");
		}else {
			a = (Atom) x.getContents().get(0);
			b = (Atom) x.getContents().get(1);
			if(a.getBool() || b.getBool()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
	static public Atom LNot(LList x) throws IOException {
		Atom a;
		if(x.size() != 2) {
			throw new IOException("Function 'and': expected 2 arguments, recived " + x.size());
		}else if(!(x.getContents().get(0) instanceof Atom)) {
			throw new IOException("Function 'and': " + x.getContents().get(0).printName() + " is not a symbol");
		}else {
			a = (Atom) x.getContents().get(0);
			if(!a.getBool()) {
				return new Atom("T", true, 0);
			}else {
				return new Atom("NIL", true, 0);
			}
		}
	}
	
}
