package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NameAliasesTest {
	NameAliases sut;

	@BeforeEach
	void setUp() {
		sut = new NameAliases();
	}

	@Nested
	class GetAlias {
		@Test
		void zero() {
			// Exercise
			String actual = sut.getAlias(0x21);
			// Verify
			assertNull(actual);
		}

		@Test
		void one() {
			// Exercise
			String actual = sut.getAlias(0x20);
			// Verify
			assertEquals("SP", actual);
		}

		@Test
		void many() {
			// Exercise
			String actual = sut.getAlias(0x9);
			// Verify
			assertEquals("CHARACTER TABULATION, HORIZONTAL TABULATION, HT, TAB", actual);
		}
	}

	@Nested
	class Grep {
		@Test
		void simpleWord() {
			// Exercise
			List<Integer> actual = sut.grep("TABULATION");
			// Verify
			List<Integer> expected = new ArrayList<>();
			expected.add(0x9);
			expected.add(0xB);
			expected.add(0x88);
			expected.add(0x89);
			expected.add(0x8A);
			assertIterableEquals(expected, actual);
		}
	}
}
