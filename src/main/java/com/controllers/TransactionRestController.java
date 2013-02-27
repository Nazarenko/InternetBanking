package com.controllers;

import com.model.Client;
import com.model.ClientStatus;
import com.model.Transaction;
import com.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 2/27/13
 * Time: 11:42 AM
 */
@Controller
public class TransactionRestController {

    @Autowired
    private AppService appService;

    public AppService getDbService() {
        return appService;
    }

    public void setDbService(AppService appService) {
        this.appService = appService;
    }

    /**
     * Creates transaction from ajax request
     *
     * @param source
     *            - account number
     * @param destination
     *            - destination account number
     *
     * @param sum
     *            - transaction sum
     * @return list of errors for validation if failed, otherwise empty list
     */
    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap createTransaction(@RequestParam String source,
                               @RequestParam String destination,
                               @RequestParam BigDecimal sum) {

        ModelMap error = new ModelMap("result", "error");

        appService.createTransaction(source, destination, sum);

        return new ModelMap("result", "success");
    }

    /**
     * Returns last transactions for user
     * @param number - account number
     */
    @RequestMapping(value = "/transactionsLast", method = RequestMethod.GET)
    public @ResponseBody
    List<Transaction> transactionsLast(@RequestParam String number) {
        return appService.findClientLastTransactions(number, 5);
    }

    /**
     * Returns transactions list for user
     * @param number - account number
     * @param page
     *            - page number of pagination
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public @ResponseBody
    List<Transaction> transactionsList(@RequestParam String number,
                                       @RequestParam(required = false, defaultValue = "1") int page) {
        return appService.findClientTransactions(number, page);
    }


    /**
     * Returns pages count of transactions list for  user
     * @param number - account number
     */
    @RequestMapping(value = "/transactionsPages", method = RequestMethod.GET)
    public @ResponseBody
    ModelMap transactionsPages(@RequestParam String number) {

        ModelMap model = new ModelMap();
        Integer pages = appService.getTransactionsPages(number);
        model.addAttribute("pages", pages);
        return model;
    }
}
