package cloud.operations.controller.admin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {
	// Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	// Index - Admin Panel (temp)
	@GetMapping("/")
	public String loginSuccess(Model model) {
		return "admin";
	}

}