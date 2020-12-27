
public class Atom extends LispObject {
	private String valueS;
	private int valueI;
	private boolean valueB;
	private boolean num;
	
	Atom(String value, boolean literal, int numQuotes) {
		super(literal, numQuotes);
		this.valueS = value;			//String value for an atom is always defined

		if(value.matches("-?\\d+")) {	//This atom is an integer
			this.valueI = Integer.parseInt(value);
			this.valueB = true;
			this.num = true;
			super.literal = true;
			
		}else if(value.equals("NIL")) {	//This atom is boolean false
			this.valueB = false;
			this.num = false;
			super.literal = true;
			
		}else if(value.equals("T")) { //this atom is boolean true
			this.valueB = true;
			this.num = false;
			super.literal = true;
			
		}else {						 // this atom is a string
			this.valueB = true;
			this.num = false;
		}
	}
	
	public int getNum() {
		return this.valueI;
	}
	
	public boolean getBool() {
		return this.valueB;
	}
	
	public boolean isNum() {
		if(this.num) {
			return true;
		}else {
			return false;
		}
	}
	
	public String printName() {
		String returnMe = new String("");
		for(int i = 0; i < super.numQuotes; i++) {
			returnMe = returnMe.concat("'");
		}
		return returnMe.concat(this.valueS);
	}

}
