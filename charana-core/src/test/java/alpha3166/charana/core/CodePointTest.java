package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CodePointTest {
	@Nested
	class InstanceMethods {
		@Test
		void ascii() {
			// Exercise
			CodePoint actual = new CodePoint(0x41);
			// Verify
			assertEquals(0x41, actual.getValue());
			assertEquals("A", actual.getChar());
			assertEquals("U+0041", actual.getHex());
			assertEquals("LATIN CAPITAL LETTER A", actual.getName());
			assertEquals(Integer.hashCode(0x41), actual.hashCode());
			assertEquals("A: U+0041 LATIN CAPITAL LETTER A", actual.toString());
		}

		@Test
		void latin1() {
			// Exercise
			CodePoint actual = new CodePoint(0x108);
			// Verify
			assertEquals(0x108, actual.getValue());
			assertEquals("Ĉ", actual.getChar());
			assertEquals("U+0108", actual.getHex());
			assertEquals("LATIN CAPITAL LETTER C WITH CIRCUMFLEX", actual.getName());
			assertEquals(Integer.hashCode(0x108), actual.hashCode());
			assertEquals("Ĉ: U+0108 LATIN CAPITAL LETTER C WITH CIRCUMFLEX", actual.toString());
		}

		@Test
		void ideograph() {
			// Exercise
			CodePoint actual = new CodePoint(0x6F22);
			// Verify
			assertEquals(0x6F22, actual.getValue());
			assertEquals("漢", actual.getChar());
			assertEquals("U+6F22", actual.getHex());
			assertEquals("CJK UNIFIED IDEOGRAPH-6F22", actual.getName());
			assertEquals(Integer.hashCode(0x6F22), actual.hashCode());
			assertEquals("漢: U+6F22 CJK UNIFIED IDEOGRAPH-6F22", actual.toString());
		}

		@Test
		void sip() {
			// Exercise
			CodePoint actual = new CodePoint(0x20BB7);
			// Verify
			assertEquals(0x20BB7, actual.getValue());
			assertEquals("𠮷", actual.getChar());
			assertEquals("U+20BB7", actual.getHex());
			assertEquals("CJK UNIFIED IDEOGRAPH-20BB7", actual.getName());
			assertEquals(Integer.hashCode(0x20BB7), actual.hashCode());
			assertEquals("𠮷: U+20BB7 CJK UNIFIED IDEOGRAPH-20BB7", actual.toString());
		}

		@Test
		void control() {
			// Exercise
			CodePoint actual = new CodePoint(0x9);
			// Verify
			assertEquals(0x9, actual.getValue());
			assertEquals("\t", actual.getChar());
			assertEquals("U+0009", actual.getHex());
			assertEquals("CHARACTER TABULATION", actual.getName());
			assertEquals(Integer.hashCode(0x9), actual.hashCode());
			assertEquals("\t: U+0009 CHARACTER TABULATION", actual.toString());
		}
	}

	@Test
	void testEquals() {
		// Setup
		CodePoint sut = new CodePoint(0x41);
		// Exercise & Verify
		assertFalse(sut.equals(null));
		assertFalse(sut.equals(new Object()));
		assertFalse(sut.equals(new CodePoint(0x42)));
		assertTrue(sut.equals(new CodePoint(0x41)));
	}

	@Nested
	class Parse {
		@Test
		void oneToken() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("U+6F22");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x6F22));
			assertIterableEquals(expected, actual);
		}

		@Test
		void smallCase() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("U+6f22");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x6F22));
			assertIterableEquals(expected, actual);
		}

		@Test
		void manyTokens() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("<U+9 U+41  U+108,U+6F22,,U+20BB7>");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x9));
			expected.add(new CodePoint(0x41));
			expected.add(new CodePoint(0x108));
			expected.add(new CodePoint(0x6F22));
			expected.add(new CodePoint(0x20BB7));
			assertIterableEquals(expected, actual);
		}

		@Test
		void prefixlessTokens() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse(" 9 41 108 6F22 20BB7 ");
			// Verify
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x9));
			expected.add(new CodePoint(0x41));
			expected.add(new CodePoint(0x108));
			expected.add(new CodePoint(0x6F22));
			expected.add(new CodePoint(0x20BB7));
			assertIterableEquals(expected, actual);
		}

		@Test
		void emptyString() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void noHexString() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("U+6F2X");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void tooLargeCodePoint() {
			// Exercise
			List<CodePoint> actual = CodePoint.parse("U+110000");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Format {
		@Test
		void one() {
			// Setup
			List<CodePoint> list = new ArrayList<>();
			list.add(new CodePoint(0x41));
			// Exercise
			String actual = CodePoint.format(list);
			// Verify
			assertEquals("U+0041", actual);
		}

		@Test
		void many() {
			// Setup
			List<CodePoint> list = new ArrayList<>();
			list.add(new CodePoint(0x9));
			list.add(new CodePoint(0x41));
			list.add(new CodePoint(0x108));
			list.add(new CodePoint(0x6F22));
			list.add(new CodePoint(0x20BB7));
			// Exercise
			String actual = CodePoint.format(list);
			// Verify
			assertEquals("<U+0009,U+0041,U+0108,U+6F22,U+20BB7>", actual);
		}
	}

	@Nested
	class Decompose {
		@Test
		void string() {
			// Exercise
			List<CodePoint> actual = CodePoint.decompose("\tAĈ漢𠮷");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x9));
			expected.add(new CodePoint(0x41));
			expected.add(new CodePoint(0x108));
			expected.add(new CodePoint(0x6F22));
			expected.add(new CodePoint(0x20BB7));
			assertIterableEquals(expected, actual);
		}

		@Test
		void emptyString() {
			// Exercise
			List<CodePoint> actual = CodePoint.decompose("");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Compose {
		@Test
		void list() {
			// Setup
			List<CodePoint> list = new ArrayList<>();
			list.add(new CodePoint(0x9));
			list.add(new CodePoint(0x41));
			list.add(new CodePoint(0x108));
			list.add(new CodePoint(0x6F22));
			list.add(new CodePoint(0x20BB7));
			// Exercise
			String actual = CodePoint.compose(list);
			// Verify
			assertEquals("\tAĈ漢𠮷", actual);
		}

		@Test
		void emptyList() {
			// Setup
			List<CodePoint> list = new ArrayList<>();
			// Exercise
			String actual = CodePoint.compose(list);
			// Verify
			assertEquals("", actual);
		}
	}

	@Nested
	class FindByName {
		@Test
		void simpleWord() {
			// Exercise
			List<CodePoint> actual = CodePoint.findByName("calendar");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x1F4C5));
			expected.add(new CodePoint(0x1F4C6));
			expected.add(new CodePoint(0x1F5D3));
			assertIterableEquals(expected, actual);
		}

		@Test
		void head() {
			// Exercise
			List<CodePoint> actual = CodePoint.findByName("^copyright");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x00A9));
			assertIterableEquals(expected, actual);
		}

		@Test
		void tail() {
			// Exercise
			List<CodePoint> actual = CodePoint.findByName("copyright$");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x2117));
			assertIterableEquals(expected, actual);
		}

		@Test
		void nonAscii() {
			// Exercise
			List<CodePoint> actual = CodePoint.findByName("漢字");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void emptyString() {
			// Exercise
			List<CodePoint> actual = CodePoint.findByName("");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}
