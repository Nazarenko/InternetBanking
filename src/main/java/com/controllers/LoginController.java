package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	/**
	 * Returns login page, shows last secure error if required
	 * 
	 * @param error
	 *            - show error message
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(required = false) boolean error) {
		ModelAndView mav = new ModelAndView("login");
		if (error) {
			mav.addObject("error", "true");
		}
		return mav;
	}
}
