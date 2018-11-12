package alpha3166.charana.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodePoint {
	private static DerivedName nameDb = new DerivedName();

	private int value;

	public CodePoint(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getChar() {
		return new String(Character.toChars(value));
	}

	public String getHex() {
		return String.format("U+%04X", value);
	}

	public String getName() {
		return nameDb.getName(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof CodePoint)) {
			return false;
		}
		CodePoint other = (CodePoint) obj;
		return other.value == this.value;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(value);
	}

	@Override
	public String toString() {
		return getChar() + ": " + getHex() + " " + getName();
	}

	public static List<CodePoint> parse(String codePointSequence) {
		String[] tokens = codePointSequence.trim().replaceFirst("<(.*)>", "$1").split("[ ,]+");
		if (tokens.length == 0) {
			return Collections.emptyList();
		}
		List<CodePoint> result = new ArrayList<>();
		for (String token : tokens) {
			String hexString = token.replaceFirst("^U\\+", "");
			int codePoint;
			try {
				codePoint = Integer.parseInt(hexString, 16);
			} catch (NumberFormatException e) {
				return Collections.emptyList();
			}
			if (codePoint > 0x10FFFF) {
				return Collections.emptyList();
			}
			result.add(new CodePoint(codePoint));
		}
		return result;
	}

	public static String format(List<CodePoint> codePoints) {
		StringBuilder result = new StringBuilder();
		for (CodePoint codePoint : codePoints) {
			if (result.length() > 0) {
				result.append(",");
			}
			result.append(String.format("U+%04X", codePoint.getValue()));
		}
		if (codePoints.size() >= 2) {
			result.insert(0, "<");
			result.append(">");
		}
		return result.toString();
	}

	public static List<CodePoint> decompose(String string) {
		List<CodePoint> result = new ArrayList<>();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isLowSurrogate(string.charAt(i))) {
				continue;
			}
			result.add(new CodePoint(string.codePointAt(i)));
		}
		return result;
	}

	public static String compose(List<CodePoint> codePoints) {
		StringBuilder result = new StringBuilder();
		for (CodePoint codePoint : codePoints) {
			result.append(codePoint.getChar());
		}
		return result.toString();
	}

	public static List<CodePoint> findByName(String regex) {
		if (!regex.matches("\\p{ASCII}+")) {
			return Collections.emptyList();
		}
		List<CodePoint> result = new ArrayList<>();
		for (int codePoint : nameDb.grep(regex)) {
			result.add(new CodePoint(codePoint));
		}
		return result;
	}
}
