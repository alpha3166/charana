package alpha3166.charana.core;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class Analyzer {
	String string;
	List<CodePoint> parsed;
	String parsedComposed;
	String parsedFormatted;
	List<CodePoint> decomposed;
	String decomposedFormatted;
	List<CodePoint> foundByName;
	Map<String, String> escaped;
	Map<String, String> unescaped;
	Map<Charset, String> encoded;
	Map<Charset, String> decoded;

	public Analyzer(String string) {
		this.string = string == null ? "" : string;

		parsed = CodePoint.parse(this.string);
		parsedComposed = CodePoint.compose(parsed);
		parsedFormatted = CodePoint.format(parsed);
		decomposed = CodePoint.decompose(this.string);
		decomposedFormatted = CodePoint.format(decomposed);
		foundByName = CodePoint.findByName(this.string);
		escaped = MultiEscaper.escape(this.string);
		unescaped = MultiEscaper.unescape(this.string);
		encoded = MultiEncoder.encode(this.string);
		decoded = MultiEncoder.decode(this.string);
	}

	public String getString() {
		return string;
	}

	public List<CodePoint> getParsed() {
		return parsed;
	}

	public String getParsedComposed() {
		return parsedComposed;
	}

	public String getParsedFormatted() {
		return parsedFormatted;
	}

	public List<CodePoint> getDecomposed() {
		return decomposed;
	}

	public String getDecomposedFormatted() {
		return decomposedFormatted;
	}

	public List<CodePoint> getFoundByName() {
		return foundByName;
	}

	public Map<String, String> getEscaped() {
		return escaped;
	}

	public Map<String, String> getUnescaped() {
		return unescaped;
	}

	public Map<Charset, String> getEncoded() {
		return encoded;
	}

	public Map<Charset, String> getDecoded() {
		return decoded;
	}
}
