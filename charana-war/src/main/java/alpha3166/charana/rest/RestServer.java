package alpha3166.charana.rest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import alpha3166.charana.core.CodePoint;
import alpha3166.charana.core.MultiEncoder;
import alpha3166.charana.core.MultiEscaper;

@Path("/")
public class RestServer {
	@GET
	@Path("/parse/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<List<String>> parse(@PathParam("string") String string) {
		return transformCodePointList(CodePoint.parse(string));
	}

	@GET
	@Path("/decompose/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<List<String>> decompose(@PathParam("string") String string) {
		return transformCodePointList(CodePoint.decompose(string));
	}

	@GET
	@Path("/findByName/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<List<String>> findByName(@PathParam("string") String string) {
		return transformCodePointList(CodePoint.findByName(string));
	}

	private List<List<String>> transformCodePointList(List<CodePoint> list) {
		List<List<String>> result = new ArrayList<>();
		for (CodePoint item : list) {
			List<String> record = new ArrayList<>();
			record.add(item.getChar());
			record.add(item.getHex());
			record.add(item.getName());
			result.add(record);
		}
		return result;
	}

	@GET
	@Path("/encode/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> encode(@PathParam("string") String string) {
		return transformCharsetMap(MultiEncoder.encode(string));
	}

	@GET
	@Path("/decode/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> decode(@PathParam("string") String string) {
		return transformCharsetMap(MultiEncoder.decode(string));
	}

	private Map<String, String> transformCharsetMap(Map<Charset, String> map) {
		Map<String, String> result = new TreeMap<>();
		for (Charset charset : map.keySet()) {
			result.put(charset.name(), map.get(charset));
		}
		return result;
	}

	@GET
	@Path("/escape/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> escape(@PathParam("string") String string) {
		return MultiEscaper.escape(string);
	}

	@GET
	@Path("/unescape/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> unescape(@PathParam("string") String string) {
		return MultiEscaper.unescape(string);
	}
}
