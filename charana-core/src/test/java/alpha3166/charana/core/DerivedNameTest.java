package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DerivedNameTest {
	DerivedName sut;

	@BeforeEach
	void setUp() {
		sut = new DerivedName();
	}

	@Nested
	class RangeTest {
		@Test
		void bmp() {
			// Exercise
			var range = sut.new Range("3400..4DB5", "CJK UNIFIED IDEOGRAPH-*");
			// Verify
			assertFalse(range.contains(0x33FF));
			assertTrue(range.contains(0x3400));
			assertTrue(range.contains(0x4DB5));
			assertFalse(range.contains(0x4DB6));
			assertEquals("CJK UNIFIED IDEOGRAPH-340A", range.getName(0x340A));
		}

		@Test
		void sip() {
			// Exercise
			var range = sut.new Range("2F800..2FA1D", "CJK COMPATIBILITY IDEOGRAPH-*");
			// Verify
			assertFalse(range.contains(0x2F7FF));
			assertTrue(range.contains(0x2F800));
			assertTrue(range.contains(0x2FA1D));
			assertFalse(range.contains(0x2FA1E));
			assertEquals("CJK COMPATIBILITY IDEOGRAPH-2F800", range.getName(0x2F800));
		}
	}

	@Nested
	class GetName {
		@Test
		void ascii() {
			// Exercise
			var actual = sut.getName(0x41);
			// Verify
			assertEquals("LATIN CAPITAL LETTER A", actual);
		}

		@Test
		void ideograph() {
			// Exercise
			var actual = sut.getName(0x3400);
			// Verify
			assertEquals("CJK UNIFIED IDEOGRAPH-3400", actual);
		}

		@Test
		void sip() {
			// Exercise
			var actual = sut.getName(0x2000B);
			// Verify
			assertEquals("CJK UNIFIED IDEOGRAPH-2000B", actual);
		}

		@Test
		void tooLargeCodePoint() {
			// Exercise
			var actual = sut.getName(0x110000);
			// Verify
			assertNull(actual);
		}
	}

	@Nested
	class Grep {
		@Test
		void simpleWord() {
			// Exercise
			var actual = sut.grep("CALENDAR");
			// Verify
			List<Integer> expected = new ArrayList<>();
			expected.add(0x1F4C5);
			expected.add(0x1F4C6);
			expected.add(0x1F5D3);
			assertIterableEquals(expected, actual);
		}
	}
}
