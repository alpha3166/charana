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

public class NameAliases {
	private Map<Integer, List<String>> charMap = new HashMap<>();

	public NameAliases() {
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("/NameAliases.txt")))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.startsWith("#") || line.isEmpty()) {
					continue;
				}
				String[] tokens = line.split(";");
				String codePointStr = tokens[0];
				String name = tokens[1];
				int codePoint = Integer.parseInt(codePointStr, 16);
				List<String> nameList = charMap.get(codePoint);
				if (nameList == null) {
					nameList = new ArrayList<>();
					charMap.put(codePoint, nameList);
				}
				nameList.add(name);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getAlias(int codePoint) {
		if (!charMap.containsKey(codePoint)) {
			return null;
		}
		return charMap.get(codePoint)
				.stream()
				.collect(Collectors.joining(", "));
	}

	public List<Integer> grep(String regex) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return charMap.entrySet()
				.stream()
				.filter(e -> {
					List<String> nameList = e.getValue();
					for (String name : nameList) {
						if (pattern.matcher(name).find()) {
							return true;
						}
					}
					return false;
				})
				.map(e -> e.getKey())
				.collect(Collectors.toList());
	}
}
