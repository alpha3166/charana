package alpha3166.charana.core;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class MultiEncoder {
	private static List<Charset> soundCharsets = new ArrayList<>();
	static {
		for (var charset : Charset.availableCharsets().values()) {
			try {
				// Some charsets like ISO-2022-CN or x-JISAutoDetect are actually not available
				"".getBytes(charset);
				soundCharsets.add(charset);
			} catch (UnsupportedOperationException e) {
				// Ignore
			}
		}
	}

	public static List<Charset> availableCharsets() {
		return soundCharsets;
	}

	public static SortedMap<Charset, String> encode(String string) {
		SortedMap<Charset, String> result = new TreeMap<>();
		if (string.isEmpty()) {
			return result;
		}
		for (var charset : soundCharsets) {
			var encodedBytes = string.getBytes(charset);
			var restoredString = new String(encodedBytes, charset);
			if (restoredString.equals(string)) {
				result.put(charset, Hex.encodeHexString(encodedBytes));
			}
		}
		return result;
	}

	public static SortedMap<Charset, String> decode(String hexString) {
		SortedMap<Charset, String> result = new TreeMap<>();
		if (hexString.isEmpty()) {
			return result;
		}
		byte[] decodedBytes;
		try {
			decodedBytes = Hex.decodeHex(hexString);
		} catch (DecoderException e) {
			return Collections.emptySortedMap();
		}
		for (var charset : soundCharsets) {
			var decodedString = new String(decodedBytes, charset);
			var restoredBytes = decodedString.getBytes(charset);
			if (Arrays.equals(restoredBytes, decodedBytes)) {
				result.put(charset, decodedString);
			}
		}
		return result;
	}
}
