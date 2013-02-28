package com.controllers;

import com.exceptions.ServiceException;
import com.model.Client;
import com.model.ClientStatus;
import com.model.Transaction;
import com.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
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

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody String handleServiceException(ServiceException ex)
    {
        return ex.getMessage();
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("error", ex.getMessage());
//        return modelAndView;
//        ModelMap model = new ModelMap("result","error");
//        model.addAttribute("message", ex.getMessage());
//        return model;
    }

    /**
     * Creates transaction from ajax request
     *
     * @param body
     *            - json data
     * @return list of errors for validation if failed, otherwise empty list
     */
    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    ModelMap createTransaction(@RequestBody ModelMap body) {

        appService.createTransaction(
                body.get("source").toString(),
                body.get("destination").toString(),
                new BigDecimal((String)body.get("sum")));

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
