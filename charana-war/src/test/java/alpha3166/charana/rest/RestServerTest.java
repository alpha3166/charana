package alpha3166.charana.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class RestServerTest extends JerseyTest {
	@Override
	protected Application configure() {
		return new ResourceConfig(RestServer.class);
	}

	@Test
	public void parse() {
		// Exercise
		Response actual = target("/parse/<U+6F22, U+5B57>").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String expectedJson = "[[\"漢\",\"U+6F22\",\"CJK UNIFIED IDEOGRAPH-6F22\"],"
				+ "[\"字\",\"U+5B57\",\"CJK UNIFIED IDEOGRAPH-5B57\"]]";
		String actualJson = actual.readEntity(String.class);
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void decompose() {
		// Exercise
		Response actual = target("/decompose/漢字").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String expectedJson = "[[\"漢\",\"U+6F22\",\"CJK UNIFIED IDEOGRAPH-6F22\"],"
				+ "[\"字\",\"U+5B57\",\"CJK UNIFIED IDEOGRAPH-5B57\"]]";
		String actualJson = actual.readEntity(String.class);
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void findByName() {
		// Exercise
		Response actual = target("/findByName/calendar").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String expectedJson = "[[\"\\uD83D\\uDCC5\",\"U+1F4C5\",\"CALENDAR\"],"
				+ "[\"\\uD83D\\uDCC6\",\"U+1F4C6\",\"TEAR-OFF CALENDAR\"],"
				+ "[\"\\uD83D\\uDDD3\",\"U+1F5D3\",\"SPIRAL CALENDAR PAD\"]]";
		String actualJson = actual.readEntity(String.class);
		assertEquals(expectedJson, actualJson);
	}

	@Test
	public void encode() {
		// Exercise
		Response actual = target("/encode/漢字").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String actualJson = actual.readEntity(String.class);
		assertTrue(actualJson.contains("\"Shift_JIS\":\"8abf8e9a\""));
		assertTrue(actualJson.contains("\"UTF-8\":\"e6bca2e5ad97\""));
	}

	@Test
	public void decode() {
		// Exercise
		Response actual = target("/decode/e6bca2e5ad97").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String actualJson = actual.readEntity(String.class);
		assertTrue(actualJson.contains("\"UTF-8\":\"漢字\""));
	}

	@Test
	public void escape() {
		// Exercise
		Response actual = target("/escape/漢字").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String actualJson = actual.readEntity(String.class);
		assertTrue(actualJson.contains("\"Java\":\"\\\\u6F22\\\\u5B57\""));
	}

	@Test
	public void unescape() {
		// Exercise
		Response actual = target("/unescape/\\u6F22\\u5B57").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		String actualJson = actual.readEntity(String.class);
		assertTrue(actualJson.contains("\"Java\":\"漢字\""));
	}
}
