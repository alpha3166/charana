package alpha3166.charana.cli;

import java.util.ArrayList;
import java.util.List;

import alpha3166.charana.core.CodePoint;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;

public class CharanaCLI {
	public static void main(String... args) {
		for (String arg : args) {
			System.out.println("----------------");
			analyze(arg).forEach(System.out::println);
		}
	}

	public static List<String> analyze(String string) {
		List<String> result = new ArrayList<>();
		result.add("Subject: " + string);
		result.add("");

		result.addAll(findByName(string));
		result.addAll(parse(string));
		result.addAll(unescape(string));
		result.addAll(decode(string));
		result.addAll(decompose(string));
		result.addAll(escape(string));
		result.addAll(encode(string));

		return result;
	}

	static List<String> findByName(String string) {
		List<String> result = new ArrayList<>();
		var foundByName = CodePoint.findByName(string);
		if (foundByName.size() > 0) {
			result.add("Grep:");
			for (CodePoint item : foundByName) {
				result.add("  - " + item);
			}
			result.add("");
		}
		return result;
	}

	static List<String> parse(String string) {
		List<String> result = new ArrayList<>();
		var parsedResult = CodePoint.parse(string);
		if (parsedResult.size() > 0) {
			result.add("Parse:");
			if (parsedResult.size() == 1) {
				result.add("  - " + parsedResult.get(0));
			} else {
				result.add("  - " + CodePoint.compose(parsedResult));
				result.add("  - " + CodePoint.format(parsedResult));
				for (CodePoint item : parsedResult) {
					result.add("  - " + item);
				}
			}
			result.add("");
		}
		return result;
	}

	static List<String> unescape(String string) {
		List<String> result = new ArrayList<>();
		var unescapedResult = MultiEscaper.unescape(string);
		if (unescapedResult.size() > 0) {
			result.add("Unescape:");
			for (String type : unescapedResult.keySet()) {
				result.add("  %s: %s".formatted(type, unescapedResult.get(type)));
			}
			result.add("");
		}
		return result;
	}

	static List<String> decode(String string) {
		List<String> result = new ArrayList<>();
		var decodedResult = MultiEncoder.decode(string);
		if (decodedResult.size() > 0) {
			result.add("Decode:");
			for (var charset : decodedResult.keySet()) {
				result.add("  %s: %s".formatted(charset.name(), decodedResult.get(charset)));
			}
			result.add("");
		}
		return result;
	}

	static List<String> decompose(String string) {
		List<String> result = new ArrayList<>();
		var decomposedResult = CodePoint.decompose(string);
		if (decomposedResult.size() > 0) {
			result.add("Decompose:");
			if (decomposedResult.size() > 1) {
				result.add("  - " + CodePoint.format(decomposedResult));
			}
			for (var item : CodePoint.decompose(string)) {
				result.add("  - " + item);
			}
			result.add("");
		}
		return result;
	}

	static List<String> escape(String string) {
		List<String> result = new ArrayList<>();
		var escapedResult = MultiEscaper.escape(string);
		if (escapedResult.size() > 0) {
			result.add("Escape:");
			for (var type : escapedResult.keySet()) {
				result.add("  %s: %s".formatted(type, escapedResult.get(type)));
			}
			result.add("");
		}
		return result;
	}

	static List<String> encode(String string) {
		List<String> result = new ArrayList<>();
		var encodedResult = MultiEncoder.encode(string);
		if (encodedResult.size() > 0) {
			result.add("Encode:");
			for (var charset : encodedResult.keySet()) {
				result.add("  %s: %s".formatted(charset.name(), encodedResult.get(charset)));
			}
			result.add("");
		}
		return result;
	}
}
