package alpha3166.charana.web;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	void parse() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/parse/<U+6F22, U+5B57>")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var expectedCodePoints = new CodePointBean[2];
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
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		var actualCodePoints = new ObjectMapper().readValue(actualJson, CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
	}

	@Test
	void decompose() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/decompose/漢字")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var expectedCodePoints = new CodePointBean[2];
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
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		var actualCodePoints = new ObjectMapper().readValue(actualJson, CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
	}

	@Test
	void findByName() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/findByName/calendar")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var expectedCodePoints = new CodePointBean[3];
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
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		var actualCodePoints = new ObjectMapper().readValue(actualJson, CodePointBean[].class);
		assertArrayEquals(expectedCodePoints, actualCodePoints);
	}

	@Test
	void encode() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/encode/漢字")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(actualJson.contains("\"Shift_JIS\":\"8abf8e9a\""));
		assertTrue(actualJson.contains("\"UTF-8\":\"e6bca2e5ad97\""));
	}

	@Test
	void decode() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/decode/e6bca2e5ad97")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(actualJson.contains("\"UTF-8\":\"漢字\""));
	}

	@Test
	void escape() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/escape/漢字")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(actualJson.contains("\"Java\":\"\\\\u6F22\\\\u5B57\""));
	}

	@Test
	void unescape() throws Exception {
		// Exercise
		var actual = mockMvc.perform(get("/api/unescape/\\u6F22\\u5B57")).andReturn();
		// Verify
		assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());
		var actualJson = actual.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertTrue(actualJson.contains("\"Java\":\"漢字\""));
	}
}
