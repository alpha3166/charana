package alpha3166.charana.faces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import alpha3166.charana.cli.CharanaCLI;

@ManagedBean
@RequestScoped
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
