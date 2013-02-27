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
public class AccountRestController {

    @Autowired
    private AppService appService;

    public AppService getDbService() {
        return appService;
    }

    public void setDbService(AppService appService) {
        this.appService = appService;
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
        return appService.findClients(page);
    }

    /**
     * Returns pages count of accounts list
     */
    @RequestMapping(value = "/accountsPages", method = RequestMethod.GET)
    public @ResponseBody
    ModelMap accountsPages() {
        ModelMap model = new ModelMap();
        Integer pages = appService.getAccountsPages();
        model.addAttribute("pages", pages);
        return model;
    }

    /**
     * Gets client full information including balance
     *
     * @param number
     *            - account number
     */
    @RequestMapping(value = "/account/{number}", method = RequestMethod.GET)
    public @ResponseBody
    ModelMap accountDetails(@PathVariable String number) {
        Client client = appService.findClientByNumber(number);
        BigDecimal balance = appService.findClientSum(client.getId());
        ModelMap model = new ModelMap();
        model.addAttribute("name",
                client.getFirstname() + " " + client.getLastname());
        model.addAttribute("number", client.getNumber());
        model.addAttribute("status", client.getStatus());
        model.addAttribute("balance", balance);
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
    @RequestMapping(value = "/accountUpdateStatus", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap updateAccountStatus(@RequestParam String number,
                                 @RequestParam("status") ClientStatus currentStatus) {
        ClientStatus nextStatus = appService.updateClientStatus(number, currentStatus);
        ModelMap model = new ModelMap();
        model.addAttribute("status", nextStatus);
        return model;
    }
}
