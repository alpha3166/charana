package alpha3166.charana.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class DerivedName {
	protected class Range {
		private int low;
		private int high;
		private String baseName;

		public Range(String range, String baseName) {
			String[] tokens = range.split("\\.\\.");
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
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/DerivedName.txt")))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				}
				String[] tokens = line.split(";");
				String codePointStr = tokens[0].trim();
				String name = tokens[1].trim();
				if (codePointStr.contains("..")) {
					Range range = new Range(codePointStr, name);
					rangeList.add(range);
				} else {
					int codePoint = Integer.parseInt(codePointStr, 16);
					charMap.put(codePoint, name);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getName(int codePoint) {
		if (Character.isISOControl(codePoint)) {
			return Character.getName(codePoint);
		}
		if (charMap.containsKey(codePoint)) {
			return charMap.get(codePoint);
		}
		for (Range range : rangeList) {
			if (range.contains(codePoint)) {
				return range.getName(codePoint);
			}
		}
		return null;
	}

	public Set<Integer> grep(String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Set<Integer> result = new TreeSet<>();
		for (Map.Entry<Integer, String> entry : charMap.entrySet()) {
			if (pattern.matcher(entry.getValue()).find()) {
				result.add(entry.getKey());
			}
		}
		return result;
	}
}
