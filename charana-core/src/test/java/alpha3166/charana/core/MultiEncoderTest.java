package alpha3166.charana.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MultiEncoderTest {
	@Test
	void availableCharsets() {
		// Exercise
		var actual = MultiEncoder.availableCharsets();
		// Verify
		assertTrue(actual.contains(Charset.forName("UTF-8")));
		assertTrue(actual.contains(Charset.forName("Shift_JIS")));
	}

	@Nested
	class Encode {
		@Test
		void encode() {
			// Exercise
			var actual = MultiEncoder.encode("漢字");
			// Verify
			assertEquals("e6bca2e5ad97", actual.get(Charset.forName("UTF-8")));
			assertEquals("8abf8e9a", actual.get(Charset.forName("Shift_JIS")));
		}

		@Test
		void encodeWithEmptyString() {
			// Exercise
			var actual = MultiEncoder.encode("");
			// Verify
			assertEquals(0, actual.size());
		}
	}

	@Nested
	class Decode {
		@Test
		void decode() {
			// Exercise
			var actual = MultiEncoder.decode("414243");
			// Verify
			assertEquals("ABC", actual.get(Charset.forName("UTF-8")));
			assertEquals("ABC", actual.get(Charset.forName("Shift_JIS")));
		}

		@Test
		void decodeWithEmptyString() {
			// Exercise
			var actual = MultiEncoder.decode("");
			// Verify
			assertEquals(0, actual.size());
		}

		@Test
		void decodeWithNoHexString() {
			// Exercise
			var actual = MultiEncoder.decode("XX");
			// Verify
			assertEquals(0, actual.size());
		}
	}
}
