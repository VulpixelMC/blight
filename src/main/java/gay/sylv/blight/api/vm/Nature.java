package gay.sylv.blight.api.vm;

public enum Nature {
	;
	
	private final char symbol;
	
	Nature(char symbol) {
		this.symbol = symbol;
		if (!Character.isUpperCase(symbol)) {
			throw new IllegalArgumentException("Nature symbols must not be lowercase characters");
		}
	}
	
	public char getSymbol() {
		return symbol;
	}
}
