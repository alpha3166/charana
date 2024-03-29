package alpha3166.charana.core;

import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.text.StringEscapeUtils;

public class MultiEscaper {
	public static SortedMap<String, String> escape(String string) {
		SortedMap<String, String> result = new TreeMap<>();

		var escapedCsv = StringEscapeUtils.escapeCsv(string);
		if (!escapedCsv.equals(string)) {
			result.put("CSV", escapedCsv);
		}

		var escapedEcmsScript = StringEscapeUtils.escapeEcmaScript(string);
		if (!escapedEcmsScript.equals(string)) {
			result.put("ECMAScript", escapedEcmsScript);
		}

		var escapedHtml3 = StringEscapeUtils.escapeHtml3(string);
		if (!escapedHtml3.equals(string)) {
			result.put("HTML3", escapedHtml3);
		}

		var escapedHtml4 = StringEscapeUtils.escapeHtml4(string);
		if (!escapedHtml4.equals(string)) {
			result.put("HTML4", escapedHtml4);
		}

		var escapedJava = StringEscapeUtils.escapeJava(string);
		if (!escapedJava.equals(string)) {
			result.put("Java", escapedJava);
		}

		var escapedJson = StringEscapeUtils.escapeJson(string);
		if (!escapedJson.equals(string)) {
			result.put("JSON", escapedJson);
		}

		var escapedXml10 = StringEscapeUtils.escapeXml10(string);
		if (!escapedXml10.equals(string)) {
			result.put("XML10", escapedXml10);
		}

		var escapedXml11 = StringEscapeUtils.escapeXml11(string);
		if (!escapedXml11.equals(string)) {
			result.put("XML11", escapedXml11);
		}

		var escapedXSI = StringEscapeUtils.escapeXSI(string);
		if (!escapedXSI.equals(string)) {
			result.put("XSI", escapedXSI);
		}

		return result;
	}

	public static SortedMap<String, String> unescape(String string) {
		SortedMap<String, String> result = new TreeMap<>();

		try {
			var unescapedCsv = StringEscapeUtils.unescapeCsv(string);
			if (!unescapedCsv.equals(string)) {
				result.put("CSV", unescapedCsv);
			}
		} catch (Exception e) {
			// StringEscapeUtils#unescapeCsv() throws StringIndexOutOfBoundsException when
			// it gets " as an argument
			// Ignore it
		}

		var unescapedEcmsScript = StringEscapeUtils.unescapeEcmaScript(string);
		if (!unescapedEcmsScript.equals(string)) {
			result.put("ECMAScript", unescapedEcmsScript);
		}

		var unescapedHtml3 = StringEscapeUtils.unescapeHtml3(string);
		if (!unescapedHtml3.equals(string)) {
			result.put("HTML3", unescapedHtml3);
		}

		var unescapedHtml4 = StringEscapeUtils.unescapeHtml4(string);
		if (!unescapedHtml4.equals(string)) {
			result.put("HTML4", unescapedHtml4);
		}

		var unescapedJava = StringEscapeUtils.unescapeJava(string);
		if (!unescapedJava.equals(string)) {
			result.put("Java", unescapedJava);
		}

		var unescapedJson = StringEscapeUtils.unescapeJson(string);
		if (!unescapedJson.equals(string)) {
			result.put("JSON", unescapedJson);
		}

		var unescapedXml = StringEscapeUtils.unescapeXml(string);
		if (!unescapedXml.equals(string)) {
			result.put("XML", unescapedXml);
		}

		var unescapedXSI = StringEscapeUtils.unescapeXSI(string);
		if (!unescapedXSI.equals(string)) {
			result.put("XSI", unescapedXSI);
		}

		return result;
	}
}
