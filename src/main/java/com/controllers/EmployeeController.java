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
import com.services.DBService;

@Controller
@RequestMapping(value = "/admin")
public class EmployeeController {

	private static int ACCOUNTS_PER_PAGE = 10;

	@Autowired
	private DBService dbService;

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

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

	/**
	 * Gets list of accounts
	 * 
	 * @param page
	 *            - page number of pagination
	 * @return accounts list
	 */
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public @ResponseBody
	List<Client> accountsList(
			@RequestParam(required = false, defaultValue = "1") int page) {
		return dbService.findClients((page - 1) * ACCOUNTS_PER_PAGE,
				ACCOUNTS_PER_PAGE);
	}

	/**
	 * Returns pages count of accounts list
	 */
	@RequestMapping(value = "/accountsPages", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap accountsPages() {
		ModelMap model = new ModelMap();
		Integer count = dbService.getClientsCount();
		Integer pages = count / ACCOUNTS_PER_PAGE;
		if (count % ACCOUNTS_PER_PAGE > 0) {
			pages++;
		}
		model.addAttribute("pages", pages);
		return model;
	}

	/**
	 * Gets client full information including balance and last transactions list
	 * 
	 * @param number
	 *            - account number
	 */
	@RequestMapping(value = "/account/{number}", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap accountDetails(@PathVariable String number) {
		Client client = dbService.findClientByNumber(number);
		BigDecimal balance = dbService.findClientSum(client.getId());
		List<Transaction> transactions = dbService.findClientTransactions(
				number, 0, 5);
		ModelMap model = new ModelMap();
		model.addAttribute("name",
				client.getFirstname() + " " + client.getLastname());
		model.addAttribute("number", client.getNumber());
		model.addAttribute("status", client.getStatus());
		model.addAttribute("balance", balance);
		model.addAttribute("transactions", transactions);
		return model;
	}

	/**
	 * Switches client status to status available for change
	 * 
	 * @param number
	 *            - account number
	 * @param currentStatus
	 *            - current account status
	 * @return new client status
	 */
	@RequestMapping(value = "/accountUpdateStatus/{number}", method = RequestMethod.POST)
	public @ResponseBody
	ModelMap updateAccountStatus(@PathVariable String number,
			@RequestParam("status") ClientStatus currentStatus) {
		ClientStatus nextStatus = dbService.updateClientStatus(number, currentStatus);
		ModelMap model = new ModelMap();
		model.addAttribute("status", nextStatus);
		return model;
	}
}