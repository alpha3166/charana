package alpha3166.charana.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CharanaCLITest {
	private String[] loadExpected() throws Exception {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement caller = stack[3];
		String callerClass = caller.getClassName();
		String callerMethod = caller.getMethodName();
		String resourceName = "/" + callerClass.replace(".", "/") + "." + callerMethod + ".txt";
		URL resourceURL = getClass().getResource(resourceName);
		URI resourceURI = resourceURL.toURI();
		Path resourcePath = Paths.get(resourceURI);
		return Files.readAllLines(resourcePath, Charset.forName("UTF-8")).toArray(new String[0]);
	}

	@Nested
	class Analyze {
		@Test
		void normalString() throws Exception {
			// Exercise
			String actual = CharanaCLI.analyze("漢字");
			// Verify
			String[] actualLines = actual.split("\\n");
			String[] expectedLines = loadExpected();
			assertArrayEquals(expectedLines, actualLines);
		}

		@Test
		void withDecode() throws Exception {
			// Exercise
			String actual = CharanaCLI.analyze("e6bca2e5ad97");
			// Verify
			String[] actualLines = actual.split("\\n");
			String[] expectedLines = loadExpected();
			assertArrayEquals(expectedLines, actualLines);
		}

		@Test
		void withGrep() throws Exception {
			// Exercise
			String actual = CharanaCLI.analyze("calendar");
			// Verify
			String[] actualLines = actual.split("\\n");
			String[] expectedLines = loadExpected();
			assertArrayEquals(expectedLines, actualLines);
		}

		@Test
		void withParse() throws Exception {
			// Exercise
			String actual = CharanaCLI.analyze("<U+6F22, U+5B57>");
			// Verify
			String[] actualLines = actual.split("\\n");
			String[] expectedLines = loadExpected();
			assertArrayEquals(expectedLines, actualLines);
		}

		@Test
		void withUnescape() throws Exception {
			// Exercise
			String actual = CharanaCLI.analyze("&#x6F22;&#x5B57;");
			// Verify
			String[] actualLines = actual.split("\\n");
			String[] expectedLines = loadExpected();
			assertArrayEquals(expectedLines, actualLines);
		}
	}
}
