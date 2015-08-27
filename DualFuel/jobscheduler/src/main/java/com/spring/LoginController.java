package com.spring;

import com.common.LoginFilter;
import com.common.OTP_Generation;
import com.entity.User;
import com.spring.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	private static Logger log = Logger.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(value = "/validateLogin", method = RequestMethod.GET)
	public String validateLogin(
			@RequestParam("emailAddress") String emailAddress,
			@RequestParam("password") String password) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		log.info(emailAddress);

		try {
			User user = loginService.getCurrentUser(emailAddress);
			if (user != null) {
				String encrpedPass = user.getPassword().toString();
				if (password.equals(encrpedPass)) {
					session.setAttribute(
							LoginFilter.OTP_VALIDATE, user);
					
					String generatedOTP = OTP_Generation.generateOTP(8);
					long time = System.currentTimeMillis();
					MantraOTP mantraOTP = new MantraOTP(generatedOTP, time);
					log.info("otpValue = " + generatedOTP);
					session.setAttribute(
							LoginFilter.OTP_SESSION_VALUE, mantraOTP);
					
				}

			}
		} catch (Exception e) {
			log.error(e);
		}

		return "redirect:/spring/optLogin.do";
	}
	
	@RequestMapping(value = "/private/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/spring/logout.do";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout2(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/spring/private/logout.htm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		
//		final Object userkey = request.getSession().getAttribute(
//				LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE);
//		
//		if (userkey != null) {
//			return "redirect:/index.jsp";
//		}
		return "login";
	}
	
	@RequestMapping(value = "/optLogin", method = RequestMethod.GET)
	public String otpPage(HttpServletRequest request) {
		
		final Object userkey = request.getSession().getAttribute(
				LoginFilter.OTP_VALIDATE);
		
		final MantraOTP mantraOTP = (MantraOTP)request.getSession().getAttribute(
				LoginFilter.OTP_SESSION_VALUE);
		
		if (userkey == null || mantraOTP == null || mantraOTP.isOld()) {
			return "redirect:/index.jsp";
		}
		
		return "otpLogin";
	}
	
	@RequestMapping(value = "/validateOTPLogin", method = RequestMethod.POST)
	public String validateOTPLogin(@RequestParam("optPassword") String optPassword, HttpServletRequest request) {
		
		final User userkey = (User)request.getSession().getAttribute(
				LoginFilter.OTP_VALIDATE);
		final MantraOTP mantraOTP = (MantraOTP)request.getSession().getAttribute(
				LoginFilter.OTP_SESSION_VALUE);
		
 		if (userkey == null || mantraOTP == null || mantraOTP.isExpired(60000)) {
			request.getSession().invalidate();
			return "redirect:/index.jsp";
		}
		
		if(optPassword.equals(mantraOTP.getOtpString())){
			request.getSession().invalidate();
			request.getSession(true).setAttribute(
					LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE, userkey);
			
			if(userkey.getIsAskForResetPassword()){
				return "redirect:/spring/private/user/resetPassword";
			}
			
		}else{
			request.getSession().invalidate();
		}
		
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value = "/private/user/resetPassword", method = RequestMethod.GET)
	public String resetPassword(Model model) {
		return "resetPassword";
	}
	
	@RequestMapping(value = "/private/user/savePassword", method = RequestMethod.POST)
	public String add(Model model, HttpServletRequest request, @RequestParam("newPassword") String newPassword) {
		
		final User user = (User)request.getSession().getAttribute(
				LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE);
		
		if(newPassword != null && newPassword.length() > 5){
			user.setPassword(newPassword);
			user.setIsAskForResetPassword(false);
			loginService.saveOrUpdateUser(user);
		}else{
			return "resetPassword";
		}
		
//		model.addAttribute("allAvailableJob", jobProperties.jobTypes);
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value = "/validOTPPage", method = RequestMethod.GET)
	@ResponseBody
	public String validOTPPage(HttpServletRequest request) {
		
		boolean sucess = false;
		final User user = (User)request.getSession().getAttribute(
				LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE);
		
		if(user != null){
			sucess = false;
		}else{
			final Object userkey = request.getSession().getAttribute(
					LoginFilter.OTP_VALIDATE);
			if(userkey != null){
				sucess = true;
			}
		}
		return (sucess + "").toUpperCase();
	}
	
	@RequestMapping(value = "/validLoginPage", method = RequestMethod.POST)
	@ResponseBody
	public String validLoginPage(HttpServletRequest request) {
		
		boolean sucess = true;
		final User user = (User)request.getSession().getAttribute(
				LoginFilter.CURRENT_LOGGED_USER_ATTRIBUTE);
		
		if(user != null){
			sucess = false;
		}else{
			final Object userkey = request.getSession().getAttribute(
					LoginFilter.OTP_VALIDATE);
			if(userkey != null){
				sucess = false;
			}
		}
		return (sucess + "").toUpperCase();
	}

    @RequestMapping(value = "/private/app/home", method = RequestMethod.GET)
    public String appHome(Model model) {
        return "appHome";
    }

    @RequestMapping(value = "/private/coned/home", method = RequestMethod.GET)
    public String conEdHome(Model model) {
        return "conEdHome";
    }
}
