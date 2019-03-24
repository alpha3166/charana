package alpha3166.charana.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharanaCLITest {
	@Nested
	class Analyze {
		@Test
		void normal() {
			// Exercise
			List<String> actual = CharanaCLI.analyze("漢字");
			// Verify
			assertEquals("Subject: 漢字", actual.get(0));
			assertEquals("", actual.get(1));
		}

		@Test
		void empty() {
			// Exercise
			List<String> actual = CharanaCLI.analyze("");
			// Verify
			assertEquals("Subject: ", actual.get(0));
			assertEquals("", actual.get(1));
		}
	}

	@Nested
	class FindByName {
		@Test
		void found() {
			// Exercise
			List<String> actual = CharanaCLI.findByName("calendar");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Grep:");
			expected.add("  - 📅: U+1F4C5 CALENDAR");
			expected.add("  - 📆: U+1F4C6 TEAR-OFF CALENDAR");
			expected.add("  - 🗓: U+1F5D3 SPIRAL CALENDAR PAD");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void notFound() {
			// Exercise
			List<String> actual = CharanaCLI.findByName("XXX");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Parse {
		@Test
		void oneChar() {
			// Exercise
			List<String> actual = CharanaCLI.parse("U+6F22");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Parse:");
			expected.add("  - 漢: U+6F22 CJK UNIFIED IDEOGRAPH-6F22");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void sequence() {
			// Exercise
			List<String> actual = CharanaCLI.parse("<U+6F22, U+5B57>");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Parse:");
			expected.add("  - 漢字");
			expected.add("  - <U+6F22, U+5B57>");
			expected.add("  - 漢: U+6F22 CJK UNIFIED IDEOGRAPH-6F22");
			expected.add("  - 字: U+5B57 CJK UNIFIED IDEOGRAPH-5B57");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void notApplicable() {
			// Exercise
			List<String> actual = CharanaCLI.parse("XXX");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Unescape {
		@Test
		void applicable() {
			// Exercise
			List<String> actual = CharanaCLI.unescape("&#x6F22;&#x5B57;");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Unescape:");
			expected.add("  HTML3: 漢字");
			expected.add("  HTML4: 漢字");
			expected.add("  XML: 漢字");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void notApplicable() {
			// Exercise
			List<String> actual = CharanaCLI.unescape("XXX");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Decode {
		@Test
		void applicable() {
			// Exercise
			List<String> actual = CharanaCLI.decode("e6bca2e5ad97");
			// Verify
			assertEquals("Decode:", actual.get(0));
			assertTrue(actual.contains("  UTF-8: 漢字"));
			assertEquals("", actual.get(actual.size() - 1));
		}

		@Test
		void notApplicable() {
			// Exercise
			List<String> actual = CharanaCLI.decode("XXX");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Decompose {
		@Test
		void oneChar() {
			// Exercise
			List<String> actual = CharanaCLI.decompose("漢");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Decompose:");
			expected.add("  - 漢: U+6F22 CJK UNIFIED IDEOGRAPH-6F22");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void sequence() {
			// Exercise
			List<String> actual = CharanaCLI.decompose("漢字");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Decompose:");
			expected.add("  - <U+6F22, U+5B57>");
			expected.add("  - 漢: U+6F22 CJK UNIFIED IDEOGRAPH-6F22");
			expected.add("  - 字: U+5B57 CJK UNIFIED IDEOGRAPH-5B57");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void empty() {
			// Exercise
			List<String> actual = CharanaCLI.decompose("");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Escape {
		@Test
		void applicable() {
			// Exercise
			List<String> actual = CharanaCLI.escape("漢字");
			// Verify
			List<String> expected = new ArrayList<>();
			expected.add("Escape:");
			expected.add("  ECMAScript: \\u6F22\\u5B57");
			expected.add("  JSON: \\u6F22\\u5B57");
			expected.add("  Java: \\u6F22\\u5B57");
			expected.add("");
			assertIterableEquals(expected, actual);
		}

		@Test
		void notApplicable() {
			// Exercise
			List<String> actual = CharanaCLI.escape("XXX");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Encode {
		@Test
		void normal() {
			// Exercise
			List<String> actual = CharanaCLI.encode("漢字");
			// Verify
			assertEquals("Encode:", actual.get(0));
			assertTrue(actual.contains("  EUC-JP: b4c1bbfa"));
			assertTrue(actual.contains("  ISO-2022-JP: 1b244234413b7a1b2842"));
			assertTrue(actual.contains("  Shift_JIS: 8abf8e9a"));
			assertTrue(actual.contains("  UTF-8: e6bca2e5ad97"));
			assertEquals("", actual.get(actual.size() - 1));
		}

		@Test
		void empty() {
			// Exercise
			List<String> actual = CharanaCLI.encode("");
			// Verify
			assertEquals("Encode:", actual.get(0));
			assertEquals("", actual.get(actual.size() - 1));
		}
	}
}
