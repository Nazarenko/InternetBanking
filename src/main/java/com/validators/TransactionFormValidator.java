package com.validators;

import com.model.TransactionForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/1/13
 * Time: 5:09 PM
 */
public class TransactionFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TransactionForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransactionForm form = (TransactionForm) target;
        if (form.getSource().equals(form.getDestination())) {
            errors.reject("error");
        }
    }
}
