package alpha3166.charana.web;

public class CodePointBean {
	private int value;
	private String character;
	private String hex;
	private String name;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getChar() {
		return character;
	}

	public void setChar(String character) {
		this.character = character;
	}

	public String getHex() {
		return hex;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		CodePointBean o = (CodePointBean) obj;
		return o.value == value && o.character.equals(character) && o.hex.equals(hex) && o.name.equals(name);
	}

	@Override
	public String toString() {
		return "%s: %s %s (%d)".formatted(character, hex, name, value);
	}
}
