import java.io.IOException;
import java.util.List;

public class LispFunction {
	private String name;
	private String[] formalArgs;
	private String codeBody;
	private int numArgs;
	
	LispFunction(String name, String[] formalArgs, String codeBody, int numArgs){
		this.name = name;
		this.formalArgs = formalArgs;
		this.codeBody = codeBody;
		this.numArgs = numArgs;
	}
	
	public String getName() {
		return this.name;
	}
	
	public LispObject execute(LList actualArgs) throws IOException {
		List<String> tokens;
		if(actualArgs.size() != this.numArgs) {
			throw new IOException("Function '" + this.name + "': expected " + this.numArgs + " argument(s), recieved " + actualArgs.size());
		}
			tokens = Interpreter.getTokens(this.codeBody);
			for(int i = 0; i < numArgs; i++) {
				for(int j = 0; j < tokens.size(); j++) {
					if(tokens.get(j).equals(this.formalArgs[i])) {
						tokens.set(j, actualArgs.getContents().get(i).printName());
					}
				}
			}
			
			return Interpreter.eval(Interpreter.readTokens(tokens, false));
	}
}
