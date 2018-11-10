package alpha3166.charana.core;

import java.util.ArrayList;
import java.util.List;

public class Unicoder {
	private static DerivedName nameDb = new DerivedName();

	public static CharInfo compose(String codePointString) {
		String[] tokens = codePointString.trim().replaceFirst("<(.*)>", "$1").split("[ ,]+");
		if (tokens.length == 0) {
			return null;
		}

		List<Integer> codePoints = new ArrayList<>();
		for (String token : tokens) {
			String hexString = token.replaceFirst("^U\\+", "");
			int codePoint;
			try {
				codePoint = Integer.parseInt(hexString, 16);
			} catch (NumberFormatException e) {
				return null;
			}
			if (codePoint > 0x10FFFF) {
				return null;
			}
			codePoints.add(codePoint);
		}

		StringBuilder normalized = new StringBuilder();
		for (int codePoint : codePoints) {
			if (normalized.length() > 0) {
				normalized.append(",");
			}
			normalized.append(String.format("U+%04X", codePoint));
		}
		if (codePoints.size() >= 2) {
			normalized.insert(0, "<");
			normalized.append(">");
		}

		StringBuilder string = new StringBuilder();
		for (int codePoint : codePoints) {
			string.append(Character.toChars(codePoint));
		}

		return new CharInfo(normalized.toString(), string.toString());
	}

	public static List<CharInfo> decompose(String string) {
		List<CharInfo> result = new ArrayList<>();
		for (int i = 0; i < string.length(); i++) {
			if (Character.isLowSurrogate(string.charAt(i))) {
				continue;
			}
			String character = null;
			if (Character.isHighSurrogate(string.charAt(i))) {
				character = string.substring(i, i + 2);
			} else {
				character = string.substring(i, i + 1);
			}
			String codePoint = String.format("U+%04X", string.codePointAt(i));
			String name = nameDb.getName(string.codePointAt(i));
			result.add(new CharInfo(character, codePoint + " " + name));
		}
		return result;
	}
}
