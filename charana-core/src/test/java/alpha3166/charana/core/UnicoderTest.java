package alpha3166.charana.core;

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
			CharInfo actual = Unicoder.compose("U+6F22");
			// Verify
			CharInfo expected = new CharInfo("U+6F22", "漢");
			assertEquals(expected.getLabel(), actual.getLabel());
			assertEquals(expected.getValue(), actual.getValue());
		}

		@Test
		void composeWithSmallCase() {
			// Exercise
			CharInfo actual = Unicoder.compose("U+6f22");
			// Verify
			CharInfo expected = new CharInfo("U+6F22", "漢");
			assertEquals(expected.getLabel(), actual.getLabel());
			assertEquals(expected.getValue(), actual.getValue());
		}

		@Test
		void composeWithManyTokens() {
			// Exercise
			CharInfo actual = Unicoder.compose("<U+9 U+41  U+108,U+6F22,,U+20BB7>");
			// Verify
			CharInfo expected = new CharInfo("<U+0009,U+0041,U+0108,U+6F22,U+20BB7>", "\tAĈ漢𠮷");
			assertEquals(expected.getLabel(), actual.getLabel());
			assertEquals(expected.getValue(), actual.getValue());
		}

		@Test
		void composeWithPrefixlessTokens() {
			// Exercise
			CharInfo actual = Unicoder.compose(" 9 41 108 6F22 20BB7 ");
			// Verify
			CharInfo expected = new CharInfo("<U+0009,U+0041,U+0108,U+6F22,U+20BB7>", "\tAĈ漢𠮷");
			assertEquals(expected.getLabel(), actual.getLabel());
			assertEquals(expected.getValue(), actual.getValue());
		}

		@Test
		void composeWithEmptyString() {
			// Exercise
			CharInfo actual = Unicoder.compose("");
			// Verify
			assertNull(actual);
		}

		@Test
		void composeWithNoHexString() {
			// Exercise
			CharInfo actual = Unicoder.compose("U+6F2X");
			// Verify
			assertNull(actual);
		}

		@Test
		void composeWithTooLargeCodePoint() {
			// Exercise
			CharInfo actual = Unicoder.compose("U+110000");
			// Verify
			assertNull(actual);
		}
	}

	@Nested
	class Decompose {
		@Test
		void decompose() {
			// Exercise
			List<CharInfo> actual = Unicoder.decompose("\tAĈ漢𠮷");
			// Verify
			assertEquals(5, actual.size());

			assertEquals("\t", actual.get(0).getLabel());
			assertEquals("U+0009 CHARACTER TABULATION", actual.get(0).getValue());

			assertEquals("A", actual.get(1).getLabel());
			assertEquals("U+0041 LATIN CAPITAL LETTER A", actual.get(1).getValue());

			assertEquals("Ĉ", actual.get(2).getLabel());
			assertEquals("U+0108 LATIN CAPITAL LETTER C WITH CIRCUMFLEX", actual.get(2).getValue());

			assertEquals("漢", actual.get(3).getLabel());
			assertEquals("U+6F22 CJK UNIFIED IDEOGRAPH-6F22", actual.get(3).getValue());

			assertEquals("𠮷", actual.get(4).getLabel());
			assertEquals("U+20BB7 CJK UNIFIED IDEOGRAPH-20BB7", actual.get(4).getValue());
		}

		@Test
		void decomposeWithEmptyString() {
			// Exercise
			List<CharInfo> actual = Unicoder.decompose("");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}
