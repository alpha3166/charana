package alpha3166.charana.cli;

import java.nio.charset.Charset;
import java.util.Map;

import alpha3166.charana.core.CharInfo;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;
import alpha3166.charana.core.Unicoder;

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

		CharInfo decodedAsCodePointResult = Unicoder.compose(string);
		if (decodedAsCodePointResult != null) {
			result.append("# Can be a sequence of Unicode code points:\n");
			result.append(decodedAsCodePointResult.getLabel() + ": " + decodedAsCodePointResult.getValue() + "\n");
			result.append("\n");
		}

		Map<String, String> unescapedResult = MultiEscaper.unescape(string);
		if (unescapedResult.size() > 0) {
			result.append("# Can be unescaped to:\n");
			for (String type : unescapedResult.keySet()) {
				result.append(String.format("%s: %s\n", type, unescapedResult.get(type)));
			}
			result.append("\n");
		}

		Map<Charset, String> decodeResult = MultiEncoder.decode(string);
		if (decodeResult.size() > 0) {
			result.append("# Can be a hexadecimal representation of:\n");
			for (Charset charset : decodeResult.keySet()) {
				result.append(String.format("%s: %s\n", charset.name(), decodeResult.get(charset)));
			}
			result.append("\n");
		}

		result.append("# Consists of:\n");
		for (CharInfo item : Unicoder.decompose(string)) {
			result.append(item.getLabel() + ": " + item.getValue() + "\n");
		}
		result.append("\n");

		Map<String, String> escapedResult = MultiEscaper.escape(string);
		if (escapedResult.size() > 0) {
			result.append("# Can be escaped to:\n");
			for (String type : escapedResult.keySet()) {
				result.append(String.format("%s: %s\n", type, escapedResult.get(type)));
			}
			result.append("\n");
		}

		result.append("# Can be encoded to:\n");
		Map<Charset, String> encodeResult = MultiEncoder.encode(string);
		for (Charset charset : encodeResult.keySet()) {
			result.append(String.format("%s: %s\n", charset.name(), encodeResult.get(charset)));
		}
		result.append("\n");

		return result.toString();
	}
}
