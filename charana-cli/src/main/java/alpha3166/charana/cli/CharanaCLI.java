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
		result.append("# Subject of analysis:\n");
		result.append(string + "\n");
		result.append("\n");

		List<CodePoint> parsedResult = CodePoint.parse(string);
		if (parsedResult.size() > 0) {
			result.append("# Can be a code point notation of:\n");
			if (parsedResult.size() == 1) {
				result.append(parsedResult.get(0) + "\n");
			} else {
				result.append(CodePoint.compose(parsedResult) + " ");
				result.append(CodePoint.format(parsedResult) + "\n");
				for (CodePoint item : parsedResult) {
					result.append("- " + item + "\n");
				}
			}
			result.append("\n");
		}

		Map<String, String> unescapedResult = MultiEscaper.unescape(string);
		if (unescapedResult.size() > 0) {
			result.append("# Can be an escaped notation of:\n");
			for (String type : unescapedResult.keySet()) {
				result.append(String.format("%s: %s\n", type, unescapedResult.get(type)));
			}
			result.append("\n");
		}

		Map<Charset, String> decodedResult = MultiEncoder.decode(string);
		if (decodedResult.size() > 0) {
			result.append("# Can be a hexadecimal notation of:\n");
			for (Charset charset : decodedResult.keySet()) {
				result.append(String.format("%s: %s\n", charset.name(), decodedResult.get(charset)));
			}
			result.append("\n");
		}

		List<CodePoint> decomposedResult = CodePoint.decompose(string);
		if (decomposedResult.size() > 0) {
			if (decomposedResult.size() == 1) {
				result.append("# Code point:\n");
			} else {
				result.append("# Code points:\n");
				result.append(CodePoint.format(decomposedResult) + "\n");
			}
			for (CodePoint item : CodePoint.decompose(string)) {
				result.append(item + "\n");
			}
			result.append("\n");
		}

		Map<String, String> escapedResult = MultiEscaper.escape(string);
		if (escapedResult.size() > 0) {
			result.append("# Can be escaped to:\n");
			for (String type : escapedResult.keySet()) {
				result.append(String.format("%s: %s\n", type, escapedResult.get(type)));
			}
			result.append("\n");
		}

		Map<Charset, String> encodedResult = MultiEncoder.encode(string);
		if (encodedResult.size() > 0) {
			result.append("# Can be encoded to:\n");
			for (Charset charset : encodedResult.keySet()) {
				result.append(String.format("%s: %s\n", charset.name(), encodedResult.get(charset)));
			}
			result.append("\n");
		}

		return result.toString();
	}
}
