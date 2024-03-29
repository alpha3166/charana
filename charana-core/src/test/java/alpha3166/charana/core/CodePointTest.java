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
			var actual = new CodePoint(0x41);
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
			var actual = new CodePoint(0x108);
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
			var actual = new CodePoint(0x6F22);
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
			var actual = new CodePoint(0x20BB7);
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
			var actual = new CodePoint(0x9);
			// Verify
			assertEquals(0x9, actual.getValue());
			assertEquals("\t", actual.getChar());
			assertEquals("U+0009", actual.getHex());
			assertEquals("(CHARACTER TABULATION, HORIZONTAL TABULATION, HT, TAB)", actual.getName());
			assertEquals(Integer.hashCode(0x9), actual.hashCode());
			assertEquals("\t: U+0009 (CHARACTER TABULATION, HORIZONTAL TABULATION, HT, TAB)", actual.toString());
		}

		@Test
		void space() {
			// Exercise
			var actual = new CodePoint(0x20);
			// Verify
			assertEquals(0x20, actual.getValue());
			assertEquals(" ", actual.getChar());
			assertEquals("U+0020", actual.getHex());
			assertEquals("SPACE (SP)", actual.getName());
			assertEquals(Integer.hashCode(0x20), actual.hashCode());
			assertEquals(" : U+0020 SPACE (SP)", actual.toString());
		}

		@Test
		void nonChar() {
			// Exercise
			var actual = new CodePoint(0x10FFFF);
			// Verify
			assertEquals(0x10FFFF, actual.getValue());
			assertEquals("\uDBFF\uDFFF", actual.getChar());
			assertEquals("U+10FFFF", actual.getHex());
			assertEquals("", actual.getName());
			assertEquals(Integer.hashCode(0x10FFFF), actual.hashCode());
			assertEquals("\uDBFF\uDFFF: U+10FFFF ", actual.toString());
		}
	}

	@Test
	void testEquals() {
		// Setup
		var sut = new CodePoint(0x41);
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
			var actual = CodePoint.parse("U+6F22");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x6F22));
			assertIterableEquals(expected, actual);
		}

		@Test
		void smallCase() {
			// Exercise
			var actual = CodePoint.parse("U+6f22");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x6F22));
			assertIterableEquals(expected, actual);
		}

		@Test
		void manyTokens() {
			// Exercise
			var actual = CodePoint.parse("<U+9 U+41  U+108,U+6F22,,U+20BB7>");
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
			var actual = CodePoint.parse(" 9 41 108 6F22 20BB7 ");
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
			var actual = CodePoint.parse("");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void noHexString() {
			// Exercise
			var actual = CodePoint.parse("U+6F2X");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void tooLargeCodePoint() {
			// Exercise
			var actual = CodePoint.parse("U+110000");
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
			var actual = CodePoint.format(list);
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
			var actual = CodePoint.format(list);
			// Verify
			assertEquals("<U+0009, U+0041, U+0108, U+6F22, U+20BB7>", actual);
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
			var actual = CodePoint.decompose("");
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
			var actual = CodePoint.compose(list);
			// Verify
			assertEquals("\tAĈ漢𠮷", actual);
		}

		@Test
		void emptyList() {
			// Setup
			List<CodePoint> list = new ArrayList<>();
			// Exercise
			var actual = CodePoint.compose(list);
			// Verify
			assertEquals("", actual);
		}
	}

	@Nested
	class FindByName {
		@Test
		void simpleWord() {
			// Exercise
			var actual = CodePoint.findByName("calendar");
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
			var actual = CodePoint.findByName("^copyright");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x00A9));
			assertIterableEquals(expected, actual);
		}

		@Test
		void tail() {
			// Exercise
			var actual = CodePoint.findByName("copyright$");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x2117));
			assertIterableEquals(expected, actual);
		}

		@Test
		void simpleWordOfAlias() {
			// Exercise
			var actual = CodePoint.findByName("tabulation");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x0009));
			expected.add(new CodePoint(0x000B));
			expected.add(new CodePoint(0x0088));
			expected.add(new CodePoint(0x0089));
			expected.add(new CodePoint(0x008A));
			expected.add(new CodePoint(0x2409));
			expected.add(new CodePoint(0x240B));
			assertIterableEquals(expected, actual);
		}

		@Test
		void headOfAlias() {
			// Exercise
			var actual = CodePoint.findByName("^device");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x0011));
			expected.add(new CodePoint(0x0012));
			expected.add(new CodePoint(0x0013));
			expected.add(new CodePoint(0x0014));
			expected.add(new CodePoint(0x0090));
			assertIterableEquals(expected, actual);
		}

		@Test
		void tailOfAlias() {
			// Exercise
			var actual = CodePoint.findByName("string$");
			// Verify
			List<CodePoint> expected = new ArrayList<>();
			expected.add(new CodePoint(0x0090));
			expected.add(new CodePoint(0x0098));
			assertIterableEquals(expected, actual);
		}

		@Test
		void nonAscii() {
			// Exercise
			var actual = CodePoint.findByName("漢字");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void emptyString() {
			// Exercise
			var actual = CodePoint.findByName("");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}
