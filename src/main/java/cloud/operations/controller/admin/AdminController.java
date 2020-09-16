package cloud.operations.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cloud.operations.entity.aws.AwsOrganization;
import cloud.operations.repository.aws.AwsAccountRepository;
import cloud.operations.service.aws.AwsAccountService;
import cloud.operations.service.aws.AwsOrganizationService;

@Controller
public class AdminController {
	// Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AwsOrganizationService awsOrganizationService;

	@Autowired
	private AwsAccountService awsAccountService;

	// Access Organization on web page
	@ModelAttribute("awsMasterAccount")
	public AwsOrganization masterAccountConstruct() {
		return new AwsOrganization();
	}

	// Index - Admin Panel (temp)
	@GetMapping("/")
	public String loginSuccess(Model model) {
		return "admin";
	}

	// add AWS Organization - Form Page (temp)
	@GetMapping("/aws/org/new")
	public String newAwsOrg(Model model) throws JsonParseException, JsonMappingException, IOException {
		return "awsneworganization";
	}

	// Save Organization
	@PostMapping("/aws/org/save")
	public String saveOrganization(@ModelAttribute @Valid AwsOrganization awsOrganization, BindingResult bindingResult,
			Model model)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		
		if (bindingResult.hasErrors()) {
			logger.error(bindingResult+"");
			return "/error";
		}

		//Save new AWS Master Account
		try {
			awsOrganizationService.save(awsOrganization);
		} catch (Exception e) {
			logger.error("Exception: " + e.toString());
		}

		model.addAttribute("accounts", awsOrganization.getAccounts());

		//Show Organizations
		return "accounts";
	}

	@GetMapping("/admin/aws/accounts")
	public String accounts (Model model) {

		model.addAttribute("accounts", awsAccountService.findAll());

		return "accounts";
	}
}