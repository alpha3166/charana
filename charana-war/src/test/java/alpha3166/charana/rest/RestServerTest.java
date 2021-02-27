package alpha3166.charana.rest;

import static org.junit.Assert.assertArrayEquals;
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
		CodePointBean[] expectedCodePoints = new CodePointBean[2];
		expectedCodePoints[0] = new CodePointBean();
		expectedCodePoints[0].setValue(0x6F22);
		expectedCodePoints[0].setChar("漢");
		expectedCodePoints[0].setHex("U+6F22");
		expectedCodePoints[0].setName("CJK UNIFIED IDEOGRAPH-6F22");
		expectedCodePoints[1] = new CodePointBean();
		expectedCodePoints[1].setValue(0x5B57);
		expectedCodePoints[1].setChar("字");
		expectedCodePoints[1].setHex("U+5B57");
		expectedCodePoints[1].setName("CJK UNIFIED IDEOGRAPH-5B57");
		CodePointBean[] actualCodePoints = actual.readEntity(CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
	}

	@Test
	public void decompose() {
		// Exercise
		Response actual = target("/decompose/漢字").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		CodePointBean[] expectedCodePoints = new CodePointBean[2];
		expectedCodePoints[0] = new CodePointBean();
		expectedCodePoints[0].setValue(0x6F22);
		expectedCodePoints[0].setChar("漢");
		expectedCodePoints[0].setHex("U+6F22");
		expectedCodePoints[0].setName("CJK UNIFIED IDEOGRAPH-6F22");
		expectedCodePoints[1] = new CodePointBean();
		expectedCodePoints[1].setValue(0x5B57);
		expectedCodePoints[1].setChar("字");
		expectedCodePoints[1].setHex("U+5B57");
		expectedCodePoints[1].setName("CJK UNIFIED IDEOGRAPH-5B57");
		CodePointBean[] actualCodePoints = actual.readEntity(CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
	}

	@Test
	public void findByName() {
		// Exercise
		Response actual = target("/findByName/calendar").request().get();
		// Verify
		assertEquals(Status.OK.getStatusCode(), actual.getStatus());
		assertEquals(MediaType.APPLICATION_JSON, actual.getHeaderString(HttpHeaders.CONTENT_TYPE));
		CodePointBean[] expectedCodePoints = new CodePointBean[3];
		expectedCodePoints[0] = new CodePointBean();
		expectedCodePoints[0].setValue(0x1F4C5);
		expectedCodePoints[0].setChar("📅");
		expectedCodePoints[0].setHex("U+1F4C5");
		expectedCodePoints[0].setName("CALENDAR");
		expectedCodePoints[1] = new CodePointBean();
		expectedCodePoints[1].setValue(0x1F4C6);
		expectedCodePoints[1].setChar("📆");
		expectedCodePoints[1].setHex("U+1F4C6");
		expectedCodePoints[1].setName("TEAR-OFF CALENDAR");
		expectedCodePoints[2] = new CodePointBean();
		expectedCodePoints[2].setValue(0x1F5D3);
		expectedCodePoints[2].setChar("🗓");
		expectedCodePoints[2].setHex("U+1F5D3");
		expectedCodePoints[2].setName("SPIRAL CALENDAR PAD");
		CodePointBean[] actualCodePoints = actual.readEntity(CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
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
