package alpha3166.charana.core;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;

public class MultiEscaper {
	public static Map<String, String> escape(String string) {
		Map<String, String> result = new LinkedHashMap<>();

		String escapedCsv = StringEscapeUtils.escapeCsv(string);
		if (!escapedCsv.equals(string)) {
			result.put("CSV", escapedCsv);
		}

		String escapedEcmsScript = StringEscapeUtils.escapeEcmaScript(string);
		if (!escapedEcmsScript.equals(string)) {
			result.put("ECMAScript", escapedEcmsScript);
		}

		String escapedHtml3 = StringEscapeUtils.escapeHtml3(string);
		if (!escapedHtml3.equals(string)) {
			result.put("HTML3", escapedHtml3);
		}

		String escapedHtml4 = StringEscapeUtils.escapeHtml4(string);
		if (!escapedHtml4.equals(string)) {
			result.put("HTML4", escapedHtml4);
		}

		String escapedJava = StringEscapeUtils.escapeJava(string);
		if (!escapedJava.equals(string)) {
			result.put("Java", escapedJava);
		}

		String escapedJson = StringEscapeUtils.escapeJson(string);
		if (!escapedJson.equals(string)) {
			result.put("JSON", escapedJson);
		}

		String escapedXml10 = StringEscapeUtils.escapeXml10(string);
		if (!escapedXml10.equals(string)) {
			result.put("XML1.0", escapedXml10);
		}

		String escapedXml11 = StringEscapeUtils.escapeXml11(string);
		if (!escapedXml11.equals(string)) {
			result.put("XML1.1", escapedXml11);
		}

		String escapedXSI = StringEscapeUtils.escapeXSI(string);
		if (!escapedXSI.equals(string)) {
			result.put("XSI", escapedXSI);
		}

		return result;
	}

	public static Map<String, String> unescape(String string) {
		Map<String, String> result = new LinkedHashMap<>();

		String unescapedCsv = StringEscapeUtils.unescapeCsv(string);
		if (!unescapedCsv.equals(string)) {
			result.put("CSV", unescapedCsv);
		}

		String unescapedEcmsScript = StringEscapeUtils.unescapeEcmaScript(string);
		if (!unescapedEcmsScript.equals(string)) {
			result.put("ECMAScript", unescapedEcmsScript);
		}

		String unescapedHtml3 = StringEscapeUtils.unescapeHtml3(string);
		if (!unescapedHtml3.equals(string)) {
			result.put("HTML3", unescapedHtml3);
		}

		String unescapedHtml4 = StringEscapeUtils.unescapeHtml4(string);
		if (!unescapedHtml4.equals(string)) {
			result.put("HTML4", unescapedHtml4);
		}

		String unescapedJava = StringEscapeUtils.unescapeJava(string);
		if (!unescapedJava.equals(string)) {
			result.put("Java", unescapedJava);
		}

		String unescapedJson = StringEscapeUtils.unescapeJson(string);
		if (!unescapedJson.equals(string)) {
			result.put("JSON", unescapedJson);
		}

		String unescapedXml = StringEscapeUtils.unescapeXml(string);
		if (!unescapedXml.equals(string)) {
			result.put("XML", unescapedXml);
		}

		String unescapedXSI = StringEscapeUtils.unescapeXSI(string);
		if (!unescapedXSI.equals(string)) {
			result.put("XSI", unescapedXSI);
		}

		return result;
	}
}
