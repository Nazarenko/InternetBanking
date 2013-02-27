package com.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.model.Client;
import com.model.ClientStatus;
import com.model.Transaction;
import com.services.AppService;

@Controller
@RequestMapping(value = "/admin")
public class EmployeeController {

	/**
	 * Returns admin view page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView employeePage() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		ModelAndView mav = new ModelAndView("admin");
		mav.addObject("username", auth.getName());
		return mav;
	}
}