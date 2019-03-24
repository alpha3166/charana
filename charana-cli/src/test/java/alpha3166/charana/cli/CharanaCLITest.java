package alpha3166.charana.cli;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharanaCLITest {
	private List<String> loadExpected() throws Exception {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stack[3];
		String callerClass = caller.getClassName();
		String callerMethod = caller.getMethodName();
		String resourceName = "/" + callerClass.replace(".", "/") + "." + callerMethod + ".txt";
		URL resourceURL = getClass().getResource(resourceName);
		URI resourceURI = resourceURL.toURI();
		Path resourcePath = Paths.get(resourceURI);
		return Files.readAllLines(resourcePath, StandardCharsets.UTF_8);
	}

	@Nested
	class Analyze {
		@Test
		void normalString() throws Exception {
			// Exercise
			List<String> actual = CharanaCLI.analyze("漢字");
			// Verify
			List<String> expected = loadExpected();
			assertIterableEquals(expected, actual);
		}

		@Test
		void withDecode() throws Exception {
			// Exercise
			List<String> actual = CharanaCLI.analyze("e6bca2e5ad97");
			// Verify
			List<String> expected = loadExpected();
			assertIterableEquals(expected, actual);
		}

		@Test
		void withGrep() throws Exception {
			// Exercise
			List<String> actual = CharanaCLI.analyze("calendar");
			// Verify
			List<String> expected = loadExpected();
			assertIterableEquals(expected, actual);
		}

		@Test
		void withParse() throws Exception {
			// Exercise
			List<String> actual = CharanaCLI.analyze("<U+6F22, U+5B57>");
			// Verify
			List<String> expected = loadExpected();
			assertIterableEquals(expected, actual);
		}

		@Test
		void withUnescape() throws Exception {
			// Exercise
			List<String> actual = CharanaCLI.analyze("&#x6F22;&#x5B57;");
			// Verify
			List<String> expected = loadExpected();
			assertIterableEquals(expected, actual);
		}
	}
}
