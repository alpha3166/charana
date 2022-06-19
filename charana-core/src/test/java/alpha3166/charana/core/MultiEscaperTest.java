package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultiEscaperTest {
	private final String ORIGINAL = "\t \u000B \" ' < A é π 漢";
	private final String ESCAPED_CSV = "\"\t \u000B \"\" ' < A é π 漢\"";
	private final String ESCAPED_ECMASCRIPT = "\\t \\u000B \\\" \\' < A \\u00E9 \\u03C0 \\u6F22";
	private final String ESCAPED_HTML3 = "\t \u000B &quot; ' &lt; A &eacute; π 漢";
	private final String ESCAPED_HTML4 = "\t \u000B &quot; ' &lt; A &eacute; &pi; 漢";
	private final String ESCAPED_JAVA = "\\t \\u000B \\\" ' < A \\u00E9 \\u03C0 \\u6F22";
	private final String ESCAPED_JSON = "\\t \\u000B \\\" ' < A \\u00E9 \\u03C0 \\u6F22";
	private final String ESCAPED_XML10 = "\t  &quot; &apos; &lt; A é π 漢";
	private final String ESCAPED_XML11 = "\t &#11; &quot; &apos; &lt; A é π 漢";
	private final String ESCAPED_XSI = "\\\t\\ \u000B\\ \\\"\\ \\'\\ \\<\\ A\\ é\\ π\\ 漢";

	@Nested
	class Escape {
		@Test
		void escape() {
			// Exercise
			var actual = MultiEscaper.escape(ORIGINAL);
			// Verify
			assertEquals(ESCAPED_CSV, actual.get("CSV"));
			assertEquals(ESCAPED_ECMASCRIPT, actual.get("ECMAScript"));
			assertEquals(ESCAPED_HTML3, actual.get("HTML3"));
			assertEquals(ESCAPED_HTML4, actual.get("HTML4"));
			assertEquals(ESCAPED_JAVA, actual.get("Java"));
			assertEquals(ESCAPED_JSON, actual.get("JSON"));
			assertEquals(ESCAPED_XML10, actual.get("XML10"));
			assertEquals(ESCAPED_XML11, actual.get("XML11"));
			assertEquals(ESCAPED_XSI, actual.get("XSI"));
		}

		void escapeNotHappens() {
			// Exercise
			var actual = MultiEscaper.escape("A");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Unescape {
		@Test
		void unescapeCSV() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_CSV);
			// Verify
			assertEquals(ORIGINAL, actual.get("CSV"));
		}

		@Test
		void unescapeCSVwithDoubleQuote() {
			// Exercise
			var actual = MultiEscaper.unescape("\"");
			// Verify
			assertNull(actual.get("CSV"));
		}

		@Test
		void unescapeECMAScript() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_ECMASCRIPT);
			// Verify
			assertEquals(ORIGINAL, actual.get("ECMAScript"));
		}

		@Test
		void unescapeHTML3() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_HTML3);
			// Verify
			assertEquals(ORIGINAL, actual.get("HTML3"));
		}

		@Test
		void unescapeHTML4() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_HTML4);
			// Verify
			assertEquals(ORIGINAL, actual.get("HTML4"));
		}

		@Test
		void unescapeJava() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_JAVA);
			// Verify
			assertEquals(ORIGINAL, actual.get("Java"));
		}

		@Test
		void unescapeJSON() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_JSON);
			// Verify
			assertEquals(ORIGINAL, actual.get("JSON"));
		}

		@Test
		void unescapeXML11() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_XML11);
			// Verify
			assertEquals(ORIGINAL, actual.get("XML"));
		}

		@Test
		void unescapeXSI() {
			// Exercise
			var actual = MultiEscaper.unescape(ESCAPED_XSI);
			// Verify
			assertEquals(ORIGINAL, actual.get("XSI"));
		}

		@Test
		void unescapeNotHappens() {
			// Exercise
			var actual = MultiEscaper.unescape("A");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}