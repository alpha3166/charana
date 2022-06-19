package alpha3166.charana.web;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alpha3166.charana.core.CodePoint;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;

@RestController
@RequestMapping("api")
public class ApiController {
	@GetMapping("parse/{string}")
	public List<CodePoint> parse(@PathVariable String string) {
		return CodePoint.parse(string);
	}

	@GetMapping("decompose/{string}")
	public List<CodePoint> decompose(@PathVariable String string) {
		return CodePoint.decompose(string);
	}

	@GetMapping("findByName/{string}")
	public List<CodePoint> findByName(@PathVariable String string) {
		return CodePoint.findByName(string);
	}

	@GetMapping("encode/{string}")
	public Map<Charset, String> encode(@PathVariable String string) {
		return MultiEncoder.encode(string);
	}

	@GetMapping("decode/{string}")
	public Map<Charset, String> decode(@PathVariable String string) {
		return MultiEncoder.decode(string);
	}

	@GetMapping("escape/{string}")
	public Map<String, String> escape(@PathVariable String string) {
		return MultiEscaper.escape(string);
	}

	@GetMapping("unescape/{string}")
	public Map<String, String> unescape(@PathVariable String string) {
		return MultiEscaper.unescape(string);
	}
}
