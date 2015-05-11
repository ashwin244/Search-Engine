package userInterface;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * partially referred from www.stackoverflow.com
 *
 */
@Controller
public class TextBoxController {
	
	@RequestMapping(value = "/admissionForm")
	public ModelAndView getAdmissionForm() {
		ModelAndView modelView = new ModelAndView("AdmissionForm");
				return modelView;
	}
	
	
	@RequestMapping("/hello")
	public @ResponseBody String submitAdmissionForm(@RequestParam("name") String name) {
		QueryInterface queryInterface = new QueryInterface();
		String list = queryInterface.GetSearchResults(name);
		return list;
	}
}
