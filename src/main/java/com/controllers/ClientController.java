package com.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@RequestMapping(value = "/client")
public class ClientController {

	@Autowired
	private AppService appService;

	public AppService getAppService() {
		return appService;
	}

	public void setDbService(AppService appService) {
		this.appService = appService;
	}

	/**
	 * Returns client view page with common client information
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView clientPage() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String login = auth.getName();
        Client client = appService.findClientByLogin(login);
		ModelAndView mav = new ModelAndView("client");

		mav.addObject("name",
				client.getFirstname() + " " + client.getLastname());
        mav.addObject("number", client.getNumber());
		return mav;
	}

}
