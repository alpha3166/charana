package alpha3166.charana.rest;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

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
	public List<CodePoint> parse(@PathParam("string") String string) {
		return CodePoint.parse(string);
	}

	@GET
	@Path("/decompose/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CodePoint> decompose(@PathParam("string") String string) {
		return CodePoint.decompose(string);
	}

	@GET
	@Path("/findByName/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CodePoint> findByName(@PathParam("string") String string) {
		return CodePoint.findByName(string);
	}

	@GET
	@Path("/encode/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Charset, String> encode(@PathParam("string") String string) {
		return MultiEncoder.encode(string);
	}

	@GET
	@Path("/decode/{string}")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Charset, String> decode(@PathParam("string") String string) {
		return MultiEncoder.decode(string);
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
