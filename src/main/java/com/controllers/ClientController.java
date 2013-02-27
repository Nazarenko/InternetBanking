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
import com.services.DBService;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

	private static int TRANSACTIONS_PER_PAGE = 10;

	@Autowired
	private DBService dbService;

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	/**
	 * Returns client view page with common client information
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView clientPage() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String login = auth.getName();
        Client client = dbService.findClientByLogin(login);
		ModelAndView mav = new ModelAndView("client");

		mav.addObject("name",
				client.getFirstname() + " " + client.getLastname());
        mav.addObject("number", client.getNumber());
		return mav;
	}

	/**
	 * Returns transactions list for current user
	 * 
	 * @param page
	 *            - page number of pagination
	 */
	@RequestMapping(value = "{number}/transactions", method = RequestMethod.GET)
	public @ResponseBody
	List<Transaction> transactionsList(@PathVariable String number,
			@RequestParam(required = false, defaultValue = "1") int page) {
		return dbService.findClientTransactions(number, (page - 1)
				* TRANSACTIONS_PER_PAGE, TRANSACTIONS_PER_PAGE);
	}

	/**
	 * Returns pages count of transactions list for current user
	 */
	@RequestMapping(value = "{number}/transactionsPages", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap transactionsPages(@PathVariable String number) {

		ModelMap model = new ModelMap();
		Integer count = dbService.getTransactionsCount(number);
		Integer pages = count / TRANSACTIONS_PER_PAGE;
		if (count % TRANSACTIONS_PER_PAGE > 0) {
			pages++;
		}
		model.addAttribute("pages", pages);
		return model;
	}

	/**
	 * Creates transaction from ajax request
	 * 
	 * @param number
	 *            - account number
	 * @param sum
	 *            - transaction sum
	 * @return list of errors for validation if failed, otherwise empty list
	 */
	@RequestMapping(value = "{number}/createTransaction", method = RequestMethod.POST)
	public @ResponseBody
	List<Object[]> createTransaction(@PathVariable String number,
                                     @RequestParam String destination,
			                         @RequestParam BigDecimal sum) {

		List<Object[]> errors = new ArrayList<Object[]>();

		Client clientDestination = dbService.findClientByNumber(destination);
		if (clientDestination == null) {
			errors.add(new Object[] { "number", false, "Account is not found" });
		} else if (!clientDestination.getStatus().equals(ClientStatus.ACTIVE)) {
			errors.add(new Object[] { "number", false, "Account is not active" });
		}
        Client clientSource = dbService.findClientByNumber(number);
        // check if active

		BigDecimal currentSum = dbService.findClientSum(clientSource.getId());

        if (currentSum.compareTo(sum) == -1) {
			errors.add(new Object[] { "sum", false,
					"You don't have enough money" });
		}

		if (errors.size() == 0) {
			dbService.createTransaction(clientSource, clientDestination, sum);
		}

		return errors;
	}

}
