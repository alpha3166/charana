package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UnicoderTest {
	@Nested
	class Compose {
		@Test
		void composeWithOneToken() {
			// Exercise
			String[] actual = Unicoder.compose("U+6F22");
			// Verify
			String[] expected = { "U+6F22", "漢" };
			assertArrayEquals(expected, actual);
		}

		@Test
		void composeWithSmallCase() {
			// Exercise
			String[] actual = Unicoder.compose("U+6f22");
			// Verify
			String[] expected = { "U+6F22", "漢" };
			assertArrayEquals(expected, actual);
		}

		@Test
		void composeWithManyTokens() {
			// Exercise
			String[] actual = Unicoder.compose("<U+9 U+41  U+108,U+6F22,,U+20BB7>");
			// Verify
			String[] expected = { "<U+0009,U+0041,U+0108,U+6F22,U+20BB7>", "\tAĈ漢𠮷" };
			assertArrayEquals(expected, actual);
		}

		@Test
		void composeWithPrefixlessTokens() {
			// Exercise
			String[] actual = Unicoder.compose(" 9 41 108 6F22 20BB7 ");
			// Verify
			String[] expected = { "<U+0009,U+0041,U+0108,U+6F22,U+20BB7>", "\tAĈ漢𠮷" };
			assertArrayEquals(expected, actual);
		}

		@Test
		void composeWithEmptyString() {
			// Exercise
			String[] actual = Unicoder.compose("");
			// Verify
			assertNull(actual);
		}

		@Test
		void composeWithNoHexString() {
			// Exercise
			String[] actual = Unicoder.compose("U+6F2X");
			// Verify
			assertNull(actual);
		}

		@Test
		void composeWithTooLargeCodePoint() {
			// Exercise
			String[] actual = Unicoder.compose("U+110000");
			// Verify
			assertNull(actual);
		}
	}

	@Nested
	class Decompose {
		@Test
		void decompose() {
			// Exercise
			List<String> actual = Unicoder.decompose("\tAĈ漢𠮷");
			// Verify
			assertEquals(5, actual.size());
			assertEquals("\t: U+0009 CHARACTER TABULATION", actual.get(0));
			assertEquals("A: U+0041 LATIN CAPITAL LETTER A", actual.get(1));
			assertEquals("Ĉ: U+0108 LATIN CAPITAL LETTER C WITH CIRCUMFLEX", actual.get(2));
			assertEquals("漢: U+6F22 CJK UNIFIED IDEOGRAPHS 6F22", actual.get(3));
			assertEquals("𠮷: U+20BB7 CJK UNIFIED IDEOGRAPHS EXTENSION B 20BB7", actual.get(4));
		}

		@Test
		void decomposeWithEmptyString() {
			// Exercise
			List<String> actual = Unicoder.decompose("");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}
