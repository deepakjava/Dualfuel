package com.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ValidationController {
	
	@RequestMapping(value = "/private/validate/cronExpression", method = RequestMethod.GET)
	@ResponseBody
	public String home(Model model, @RequestParam("inputCronExpression") String inputCronExpression) {
		boolean isValid = org.quartz.CronExpression.isValidExpression(inputCronExpression);
		return (isValid + "").toUpperCase();
	}

}
