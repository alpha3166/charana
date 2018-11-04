package alpha3166.charana.faces;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private List<CharInfo> decomposed = new ArrayList<>();
	private List<CharInfo> escaped = new ArrayList<>();
	private List<CharInfo> unescaped = new ArrayList<>();
	private List<CharInfo> encoded = new ArrayList<>();
	private List<CharInfo> decoded = new ArrayList<>();

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public CharInfo getComposed() {
		return composed;
	}

	public List<CharInfo> getDecomposed() {
		return decomposed;
	}

	public List<CharInfo> getEscaped() {
		return escaped;
	}

	public List<CharInfo> getUnescaped() {
		return unescaped;
	}

	public List<CharInfo> getEncoded() {
		return encoded;
	}

	public List<CharInfo> getDecoded() {
		return decoded;
	}

	public String analyze() {
		if (string == null) {
			return null;
		}

		composed = Unicoder.compose(string);

		decomposed = Unicoder.decompose(string);

		Map<String, String> escapedMap = MultiEscaper.escape(string);
		for (String label : escapedMap.keySet()) {
			escaped.add(new CharInfo(label, escapedMap.get(label)));
		}

		Map<String, String> unescapedMap = MultiEscaper.unescape(string);
		for (String label : unescapedMap.keySet()) {
			unescaped.add(new CharInfo(label, unescapedMap.get(label)));
		}

		Map<Charset, String> encodedMap = MultiEncoder.encode(string);
		for (Charset charset : encodedMap.keySet()) {
			encoded.add(new CharInfo(charset.name(), encodedMap.get(charset)));
		}

		Map<Charset, String> decodedMap = MultiEncoder.decode(string);
		for (Charset charset : decodedMap.keySet()) {
			decoded.add(new CharInfo(charset.name(), decodedMap.get(charset)));
		}

		return null;
	}
}
