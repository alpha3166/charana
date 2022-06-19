package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class AnalyzerTest {
	@Test
	void testNull() {
		// Exercise
		var sut = new Analyzer(null);
		// Verify
		assertEquals("", sut.getString());
		assertEquals(0, sut.getParsed().size());
		assertEquals("", sut.getParsedComposed());
		assertEquals("", sut.getParsedFormatted());
		assertEquals(0, sut.getDecomposed().size());
		assertEquals("", sut.getDecomposedFormatted());
		assertEquals(0, sut.getFoundByName().size());
		assertEquals(0, sut.getEscaped().size());
		assertEquals(0, sut.getUnescaped().size());
		assertEquals(0, sut.getEncoded().size());
		assertEquals(0, sut.getDecoded().size());
	}

	@Test
	void testEmptyString() {
		// Exercise
		var sut = new Analyzer("");
		// Verify
		assertEquals("", sut.getString());
		assertEquals(0, sut.getParsed().size());
		assertEquals("", sut.getParsedComposed());
		assertEquals("", sut.getParsedFormatted());
		assertEquals(0, sut.getDecomposed().size());
		assertEquals("", sut.getDecomposedFormatted());
		assertEquals(0, sut.getFoundByName().size());
		assertEquals(0, sut.getEscaped().size());
		assertEquals(0, sut.getUnescaped().size());
		assertEquals(0, sut.getEncoded().size());
		assertEquals(0, sut.getDecoded().size());
	}

	@Test
	void testString() {
		// Exercise
		var sut = new Analyzer("41");
		// Verify
		assertEquals("41", sut.getString());

		List<CodePoint> expectedParsed = new ArrayList<>();
		expectedParsed.add(new CodePoint(0x41));
		assertIterableEquals(expectedParsed, sut.parsed);

		assertEquals("A", sut.getParsedComposed());

		assertEquals("U+0041", sut.getParsedFormatted());

		List<CodePoint> expectedDecomposed = new ArrayList<>();
		expectedDecomposed.add(new CodePoint(0x34));
		expectedDecomposed.add(new CodePoint(0x31));
		assertIterableEquals(expectedDecomposed, sut.decomposed);

		assertEquals("<U+0034, U+0031>", sut.getDecomposedFormatted());

		assertTrue(sut.getFoundByName().size() > 0);

		assertEquals(0, sut.getEscaped().size());

		assertEquals(0, sut.getUnescaped().size());

		assertEquals("3431", sut.getEncoded().get(Charset.forName("UTF-8")));
		assertEquals("3431", sut.getEncoded().get(Charset.forName("Shift_JIS")));

		assertEquals("A", sut.getDecoded().get(Charset.forName("UTF-8")));
		assertEquals("A", sut.getDecoded().get(Charset.forName("Shift_JIS")));
	}
}
