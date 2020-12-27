import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
	static private Env env;
	
	static public Env getEnv() {
		return env;
	}
	
	static public List<String> getTokens(String line){
		line = line.replace("(", " ( ").replace(")", " ) ");
		while(line.contains("' ")) {
			line = line.replace("' ", "'");
		}
		LinkedList<String> returnMe =  new LinkedList<>(Arrays.asList(line.split(" ")));
		for(int i = 0; i < returnMe.size(); i++) {
			if(returnMe.get(i).equals("")) {
				returnMe.remove(i);
				i--;
			}
		}
		return returnMe;

	}
	
	static public LispObject readTokens(List<String> tokens, boolean literal) throws IOException {
		int numQuotes = 0;
		String token = tokens.remove(0);
		if(token.charAt(0) == '\'') {
			while(token.contains("'")) {
				token = token.replaceFirst("'", "");
				numQuotes++;
			}
			if(!literal) {
				numQuotes = 0;
				literal = true;
			}
		}
		
		
		if(token.equals("(")) {
			List<LispObject> contents = new ArrayList<>();
			while(!tokens.get(0).equals(")")) {
				contents.add(readTokens(tokens, literal));
			}
			tokens.remove(0);
			return new LList(contents, literal, numQuotes);
		}else if(token.equals(")")) {
			throw new IOException("An object cannot start with ')'");
		}else {
			return new Atom(token, literal, numQuotes);
		}
	}
	
	static private LispObject SysCar(LList a) {
		return a.getContents().get(0);
	}
	
	static private LList SysCdr(LList a) {
		return new LList(a.getContents().subList
				(1, a.size()), a.isLiteral(), 0);
	}
	
	static public LispObject eval(LispObject item) throws IOException {
		String first;
		LList rest;
		List<LispObject> args = new ArrayList<>();
		if(item.isLiteral()) {
			return item;
		}else if(item instanceof LList){
			if(((LList)item).size() < 1) {
				return new LList(new ArrayList<LispObject>(), true, 0);
			}else {
				first = SysCar((LList) item).printName();
				rest = SysCdr((LList) item);
				if(first.equals("if")) {
					if (((Atom) eval(rest.getContents().get(0))).getBool()) {
						return eval(rest.getContents().get(1));
					}else {
						return  eval(rest.getContents().get(2));
					}
				}else if(first.equals("define")) {
					return LispOperators.LDef(rest);
				}else if(first.equals("defun")) {
					return LispOperators.LFunc(rest);
				}else if(first.equals("set!")) {
					return LispOperators.LSet(rest);
				}else {
					for(int i = 0; i < rest.size(); i++) {
						args.add(eval(rest.getContents().get(i)));
					}
					return env.func(first, new LList(args, true, 0));
				}
			}
		}else {
			return env.getVar(item.printName());
		}
	}
	
	static public void interpret(String line) throws IOException {
		System.out.println(eval(readTokens(getTokens(line), false)).printName());
	}
	
	public static void main(String[] args) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("result.file")));
			Scanner input = new Scanner(System.in);
			String line = new String("");
			env = new Env(new ArrayList<LispFunction>(), new HashMap<String, LispObject>());
			
			System.out.println("Welcome to the fancy new Prompt LISP INTERPRETER, type in LISP commands!");
			writer.write("Welcome to the fancy new Prompt LISP INTERPRETER, type in LISP commands!\n");
			
			while(true) {
				line = input.nextLine();
				if(line.equals("(quit)")) {
					System.out.println("bye");
					writer.write("bye\n");
					break;
				}
				try {
					Interpreter.interpret(line);
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
					writer.write(e2.getMessage() + "\n");
				}
			}
			input.close();
			writer.close();
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
		}
		

	}
}
