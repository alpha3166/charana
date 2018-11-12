package alpha3166.charana.faces;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import alpha3166.charana.core.CodePoint;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;

@Named
@javax.enterprise.context.RequestScoped // Must be FQCN due to GlassFish bug
public class Analyzer {
	private String string;
	private List<CodePoint> parsed;
	private String parsedComposed;
	private String parsedFormatted;
	private List<CodePoint> decomposed = Collections.emptyList();
	private String decomposedFormatted;
	private Map<String, String> escaped = Collections.emptyMap();
	private Map<String, String> unescaped = Collections.emptyMap();
	private Map<Charset, String> encoded = Collections.emptyMap();
	private Map<Charset, String> decoded = Collections.emptyMap();
	private List<CodePoint> foundByName = Collections.emptyList();

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
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

	public List<CodePoint> getFoundByName() {
		return foundByName;
	}

	public String analyze() {
		if (string == null) {
			return null;
		}

		parsed = null;
		parsedComposed = null;
		parsedFormatted = null;
		decomposed = Collections.emptyList();
		decomposedFormatted = null;
		escaped = Collections.emptyMap();
		unescaped = Collections.emptyMap();
		encoded = Collections.emptyMap();
		decoded = Collections.emptyMap();
		foundByName = Collections.emptyList();

		if (string.isEmpty()) {
			return null;
		}

		parsed = CodePoint.parse(string);
		if (!parsed.isEmpty()) {
			parsedComposed = CodePoint.compose(parsed);
			parsedFormatted = CodePoint.format(parsed);
		}
		decomposed = CodePoint.decompose(string);
		if (!decomposed.isEmpty()) {
			decomposedFormatted = CodePoint.format(decomposed);
		}
		escaped = MultiEscaper.escape(string);
		unescaped = MultiEscaper.unescape(string);
		encoded = MultiEncoder.encode(string);
		decoded = MultiEncoder.decode(string);
		foundByName = CodePoint.findByName(string);
		return null;
	}
}
