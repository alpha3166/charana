package alpha3166.charana.faces;

import javax.inject.Named;

import alpha3166.charana.cli.CharanaCLI;

@Named
@javax.enterprise.context.RequestScoped // Must be FQCN due to GlassFish bug
public class Analyzer {
	private String string;
	private String result;

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String analyze() {
		result = CharanaCLI.analyze(string);
		return null;
	}
}
