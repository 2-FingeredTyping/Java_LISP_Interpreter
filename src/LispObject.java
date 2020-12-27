
public abstract class LispObject {
	protected boolean literal;
	protected int numQuotes;
	
	LispObject(boolean literal, int numQuotes){
		this.literal = literal;
		this.numQuotes = numQuotes;
	}
	
	boolean isLiteral() {
		return literal;
	}
	
	public abstract String printName();

}
