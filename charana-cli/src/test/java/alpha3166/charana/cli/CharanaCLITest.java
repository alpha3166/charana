package alpha3166.charana.cli;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharanaCLITest {
	@Nested
	class Analyze {
		@Test
		void hexString() {
			// Exercise
			String actual = CharanaCLI.analyze("ABCD");
			// Verify
			assertNotNull(actual);
		}

		@Test
		void codePointsString() {
			// Exercise
			String actual = CharanaCLI.analyze("\\u0041");
			// Verify
			assertNotNull(actual);
		}
	}
}
