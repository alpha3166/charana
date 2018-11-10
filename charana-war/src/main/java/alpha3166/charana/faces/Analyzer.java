package alpha3166.charana.faces;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Named;

import alpha3166.charana.core.CharInfo;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;
import alpha3166.charana.core.Unicoder;

@Named
@javax.enterprise.context.RequestScoped // Must be FQCN due to GlassFish bug
public class Analyzer {
	private String string;
	private CharInfo composed;
	private List<CharInfo> composedDetail;
	private List<CharInfo> decomposed = new ArrayList<>();
	private Map<String, String> escaped = new TreeMap<>();
	private Map<String, String> unescaped = new TreeMap<>();
	private Map<Charset, String> encoded = new TreeMap<>();
	private Map<Charset, String> decoded = new TreeMap<>();

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public CharInfo getComposed() {
		return composed;
	}

	public List<CharInfo> getComposedDetail() {
		return composedDetail;
	}

	public List<CharInfo> getDecomposed() {
		return decomposed;
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

	public String analyze() {
		if (string == null) {
			return null;
		}
		if (string.isEmpty()) {
			composed = null;
			composedDetail = null;
			decomposed = new ArrayList<>();
			escaped = new TreeMap<>();
			unescaped = new TreeMap<>();
			encoded = new TreeMap<>();
			decoded = new TreeMap<>();
			return null;
		}
		composed = Unicoder.compose(string);
		if (composed != null) {
			composedDetail = Unicoder.decompose(composed.getValue());
		}
		decomposed = Unicoder.decompose(string);
		escaped = MultiEscaper.escape(string);
		unescaped = MultiEscaper.unescape(string);
		encoded = MultiEncoder.encode(string);
		decoded = MultiEncoder.decode(string);
		return null;
	}
}
