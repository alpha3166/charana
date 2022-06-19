package alpha3166.charana.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import alpha3166.charana.core.Analyzer;

@Controller
@RequestMapping()
@SpringBootApplication
public class WebController {
	@GetMapping
	public String analyze(@RequestParam(defaultValue = "") String string, Model model) {
		if (model.getAttribute("inputForm") == null) {
			var inputForm = new InputForm();
			inputForm.setString(string);
			model.addAttribute(inputForm);
			model.addAttribute("result", new Analyzer(string));
		}
		return "index";
	}
}
