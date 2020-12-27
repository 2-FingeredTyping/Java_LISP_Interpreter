import java.util.List;

public class LList extends LispObject{
	private List<LispObject> contents;

	
	LList(List<LispObject> contents, boolean literal, int numQuotes) {
		super(literal, numQuotes);
		this.contents = contents;
	}
	
	public List<LispObject> getContents() {
		return this.contents;
	}
	
	public int size() {
		return this.contents.size();
	}
	
	public String printName() {
		String returnMe = new String("");
		if(this.contents.size() > 0) {
			for(int i = 0; i < super.numQuotes; i++) {
				returnMe = returnMe.concat("'");
			}
			returnMe = returnMe.concat("(");
			for(int i = 0; i < this.contents.size(); i++) {
				if(i != 0) {
					returnMe = returnMe.concat(" ");
				}
				returnMe = returnMe.concat(contents.get(i).printName());
			}
			returnMe = returnMe.concat(")");
		}else {
			returnMe = "NIL";
		}

		return returnMe;
	}
	

}
