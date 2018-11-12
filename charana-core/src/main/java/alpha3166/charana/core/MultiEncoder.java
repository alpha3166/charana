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
		for (Charset charset : Charset.availableCharsets().values()) {
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
		for (Charset charset : soundCharsets) {
			byte[] encodedBytes = string.getBytes(charset);
			String restoredString = new String(encodedBytes, charset);
			if (restoredString.equals(string)) {
				result.put(charset, Hex.encodeHexString(encodedBytes));
			}
		}
		return result;
	}

	public static SortedMap<Charset, String> decode(String hexString) {
		byte[] decodedBytes;
		try {
			decodedBytes = Hex.decodeHex(hexString);
		} catch (DecoderException e) {
			return Collections.emptySortedMap();
		}
		SortedMap<Charset, String> result = new TreeMap<>();
		for (Charset charset : soundCharsets) {
			String decodedString = new String(decodedBytes, charset);
			byte[] restoredBytes = decodedString.getBytes(charset);
			if (Arrays.equals(restoredBytes, decodedBytes)) {
				result.put(charset, decodedString);
			}
		}
		return result;
	}
}
