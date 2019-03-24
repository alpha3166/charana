package alpha3166.charana.cli;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import alpha3166.charana.core.CodePoint;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;

public class CharanaCLI {
	public static void main(String... args) {
		for (String arg : args) {
			System.out.println("----------------");
			System.out.print(analyze(arg));
		}
	}

	public static String analyze(String string) {
		StringBuilder result = new StringBuilder();
		result.append("Subject: " + string + "\n");
		result.append("\n");

		List<CodePoint> foundByName = CodePoint.findByName(string);
		if (foundByName.size() > 0) {
			result.append("Grep:\n");
			for (CodePoint item : foundByName) {
				result.append("  - " + item + "\n");
			}
			result.append("\n");
		}

		List<CodePoint> parsedResult = CodePoint.parse(string);
		if (parsedResult.size() > 0) {
			result.append("Parse:\n");
			if (parsedResult.size() == 1) {
				result.append("  - " + parsedResult.get(0) + "\n");
			} else {
				result.append("  - " + CodePoint.compose(parsedResult) + "\n");
				result.append("  - " + CodePoint.format(parsedResult) + "\n");
				for (CodePoint item : parsedResult) {
					result.append("  - " + item + "\n");
				}
			}
			result.append("\n");
		}

		Map<String, String> unescapedResult = MultiEscaper.unescape(string);
		if (unescapedResult.size() > 0) {
			result.append("Unescape:\n");
			for (String type : unescapedResult.keySet()) {
				result.append(String.format("  %s: %s\n", type, unescapedResult.get(type)));
			}
			result.append("\n");
		}

		Map<Charset, String> decodedResult = MultiEncoder.decode(string);
		if (decodedResult.size() > 0) {
			result.append("Decode:\n");
			for (Charset charset : decodedResult.keySet()) {
				result.append(String.format("  %s: %s\n", charset.name(), decodedResult.get(charset)));
			}
			result.append("\n");
		}

		List<CodePoint> decomposedResult = CodePoint.decompose(string);
		if (decomposedResult.size() > 0) {
			result.append("Decompose:\n");
			if (decomposedResult.size() > 1) {
				result.append("  - " + CodePoint.format(decomposedResult) + "\n");
			}
			for (CodePoint item : CodePoint.decompose(string)) {
				result.append("  - " + item + "\n");
			}
			result.append("\n");
		}

		Map<String, String> escapedResult = MultiEscaper.escape(string);
		if (escapedResult.size() > 0) {
			result.append("Escape:\n");
			for (String type : escapedResult.keySet()) {
				result.append(String.format("  %s: %s\n", type, escapedResult.get(type)));
			}
			result.append("\n");
		}

		Map<Charset, String> encodedResult = MultiEncoder.encode(string);
		if (encodedResult.size() > 0) {
			result.append("Encode:\n");
			for (Charset charset : encodedResult.keySet()) {
				result.append(String.format("  %s: %s\n", charset.name(), encodedResult.get(charset)));
			}
			result.append("\n");
		}

		return result.toString();
	}
}
