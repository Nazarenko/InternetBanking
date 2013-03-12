package com.controllers.rest;

import com.exceptions.DataException;
import com.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/5/13
 * Time: 9:55 AM
 */
public class ExceptionHandlerRestController {

    /**
     * Not found exceptions handler
     * @param ex {@Link Exception}
     * @return JSON response containing error
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public
    @ResponseBody
    ModelMap handleException(NotFoundException ex) {
        return new ModelMap("error", ex.getMessage());
    }

    /**
     * Data or validation exceptions handler
     * @param ex {@Link Exception}
     * @return JSON response containing error
     */
    @ExceptionHandler(DataException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    ModelMap handleException(DataException ex) {
        return new ModelMap("error", ex.getMessage());
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("error", ex.getMessage());
//        return modelAndView;
//        ModelMap model = new ModelMap("result","error");
//        model.addAttribute("message", ex.getMessage());
//        return model;
    }

//    /**
//     * Unexpected exceptions handler
//     * @param ex {@Link Exception}
//     * @return JSON response containing error
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public
//    @ResponseBody
//    ModelMap handleUnexpectedException(Exception ex) {
//        return new ModelMap("error", ex.getMessage());
//    }
}
