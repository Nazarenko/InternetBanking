package com.controllers;

import com.exceptions.NotFoundException;
import com.exceptions.ServiceException;
import com.model.Transaction;
import com.model.TransactionForm;
import com.services.AppService;
import com.validators.TransactionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private TransactionFormValidator transactionFormValidator;
    @Autowired
    private AppService appService;

    public AppService getDbService() {
        return appService;
    }

    public void setDbService(AppService appService) {
        this.appService = appService;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    String handleServiceException(NotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    String handleServiceException(ServiceException ex) {
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
     * @param transaction - json data
     * @return list of errors for validation if failed, otherwise empty list
     */
    @RequestMapping(value = "transaction", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public
    @ResponseBody
    ModelMap createTransaction(@Valid @RequestBody TransactionForm transaction) {

//        transactionFormValidator.validate(transaction,result);
//        if (result.hasErrors()) {
//            throw new ServiceException("You cannot transfer money to yourself");
//        }
        appService.createTransaction(
                transaction.getSource(),
                transaction.getDestination(),
                transaction.getSum());

        return new ModelMap("result", "success");
    }

    /**
     * Returns last transactions for user
     *
     * @param number - account number
     */
    @RequestMapping(value = "/transactionsLast", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Transaction> transactionsLast(@RequestParam String number) {
        return appService.findClientLastTransactions(number, 5);
    }

    /**
     * Returns transactions list for user
     *
     * @param number - account number
     * @param page   - page number of pagination
     */
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Transaction> transactionsList(@RequestParam String number,
                                       @RequestParam(required = false, defaultValue = "1") int page) {
        return appService.findClientTransactions(number, page);
    }


    /**
     * Returns pages count of transactions list for  user
     *
     * @param number - account number
     */
    @RequestMapping(value = "/transactionsPages", method = RequestMethod.GET)
    public
    @ResponseBody
    ModelMap transactionsPages(@RequestParam String number) {

        ModelMap model = new ModelMap();
        Integer pages = appService.getTransactionsPages(number);
        model.addAttribute("pages", pages);
        return model;
    }
}
