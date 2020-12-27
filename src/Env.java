import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Env {
	List<LispFunction> functions;
	Map<String, LispObject> variables;
	
	Env(List<LispFunction> functions, Map<String, LispObject> variables){
		this.functions = functions;
		this.variables = variables;
	}
	

	public void addVar(String name, LispObject value) {
		this.variables.put(name, value);
	}
	
	public void setVar(String name, LispObject value) {
		if(this.variables.containsKey(name)) {
			this.variables.put(name, value);
		}
	}
	
	public void addFunc(LispFunction func) {
		functions.add(func);
	}
	
	public LispObject func(String name, LList args) throws IOException {
		switch(name) {
		case "car":
			return LispOperators.car(args);
		case "cdr":
			return LispOperators.cdr(args);
		case "cons":
			return LispOperators.cons(args);
		case "+":
			return LispOperators.LAdd(args);
		case "-":
			return LispOperators.LSub(args);
		case "*":
			return LispOperators.LMult(args);
		case "/":
			return LispOperators.LDiv(args);
		case "sqrt":
			return LispOperators.LSqrt(args);
		case "pow":
			return LispOperators.LPow(args);
		case ">":
			return LispOperators.LGr(args);
		case "<":
			return LispOperators.LLs(args);
		case "=":
			return LispOperators.LEq(args);
		case "!=":
			return LispOperators.LNe(args);
		case "and":
			return LispOperators.LAnd(args);
		case "or":
			return LispOperators.LOr(args);
		case "not":
			return LispOperators.LNot(args);
		}
		for(int i = 0 ; i < functions.size(); i++) {
			if(functions.get(i).getName().equals(name)) {
				return functions.get(i).execute(args);
			}
		}
		throw new IOException("undefined function: '" + name + "'");
	}
	
	public LispObject getVar(String name) throws IOException {
		if(variables.containsKey(name)) {
			return variables.get(name);
		}else {
			throw new IOException("variable '" + name + "' has no value");
		}
	}
	
}
