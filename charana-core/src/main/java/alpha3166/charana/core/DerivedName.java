package alpha3166.charana.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DerivedName {
	protected class Range {
		private int low;
		private int high;
		private String baseName;

		public Range(String range, String baseName) {
			var tokens = range.split("\\.\\.");
			this.low = Integer.parseInt(tokens[0], 16);
			this.high = Integer.parseInt(tokens[1], 16);
			this.baseName = baseName;
		}

		public boolean contains(int codePoint) {
			return codePoint >= low && codePoint <= high;
		}

		public String getName(int codePoint) {
			return baseName.replaceFirst("\\*", Integer.toHexString(codePoint).toUpperCase());
		}
	}

	private Map<Integer, String> charMap = new HashMap<>();
	private List<Range> rangeList = new ArrayList<>();

	public DerivedName() {
		try (var in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/DerivedName.txt")))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				}
				var tokens = line.split(";");
				var codePointStr = tokens[0].trim();
				var name = tokens[1].trim();
				if (codePointStr.contains("..")) {
					var range = new Range(codePointStr, name);
					rangeList.add(range);
				} else {
					var codePoint = Integer.parseInt(codePointStr, 16);
					charMap.put(codePoint, name);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getName(int codePoint) {
		if (charMap.containsKey(codePoint)) {
			return charMap.get(codePoint);
		}
		for (var range : rangeList) {
			if (range.contains(codePoint)) {
				return range.getName(codePoint);
			}
		}
		return null;
	}

	public List<Integer> grep(String regex) {
		final var pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return charMap.entrySet().stream().filter(e -> pattern.matcher(e.getValue()).find()).map(e -> e.getKey())
				.collect(Collectors.toList());
	}
}
