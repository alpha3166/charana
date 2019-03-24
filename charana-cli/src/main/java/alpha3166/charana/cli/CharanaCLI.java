package alpha3166.charana.cli;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		List<CodePoint> foundByName = CodePoint.findByName(string);
		if (foundByName.size() > 0) {
			result.add("Grep:");
			for (CodePoint item : foundByName) {
				result.add("  - " + item);
			}
			result.add("");
		}

		List<CodePoint> parsedResult = CodePoint.parse(string);
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

		Map<String, String> unescapedResult = MultiEscaper.unescape(string);
		if (unescapedResult.size() > 0) {
			result.add("Unescape:");
			for (String type : unescapedResult.keySet()) {
				result.add(String.format("  %s: %s", type, unescapedResult.get(type)));
			}
			result.add("");
		}

		Map<Charset, String> decodedResult = MultiEncoder.decode(string);
		if (decodedResult.size() > 0) {
			result.add("Decode:");
			for (Charset charset : decodedResult.keySet()) {
				result.add(String.format("  %s: %s", charset.name(), decodedResult.get(charset)));
			}
			result.add("");
		}

		List<CodePoint> decomposedResult = CodePoint.decompose(string);
		if (decomposedResult.size() > 0) {
			result.add("Decompose:");
			if (decomposedResult.size() > 1) {
				result.add("  - " + CodePoint.format(decomposedResult));
			}
			for (CodePoint item : CodePoint.decompose(string)) {
				result.add("  - " + item);
			}
			result.add("");
		}

		Map<String, String> escapedResult = MultiEscaper.escape(string);
		if (escapedResult.size() > 0) {
			result.add("Escape:");
			for (String type : escapedResult.keySet()) {
				result.add(String.format("  %s: %s", type, escapedResult.get(type)));
			}
			result.add("");
		}

		Map<Charset, String> encodedResult = MultiEncoder.encode(string);
		if (encodedResult.size() > 0) {
			result.add("Encode:");
			for (Charset charset : encodedResult.keySet()) {
				result.add(String.format("  %s: %s", charset.name(), encodedResult.get(charset)));
			}
			result.add("");
		}

		return result;
	}
}
