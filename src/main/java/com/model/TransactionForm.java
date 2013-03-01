package com.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: tnazar
 * Date: 3/1/13
 * Time: 4:20 PM
 */
public class TransactionForm {
    @NotEmpty
    @Size(min = 16, max = 16)
    private String source;

    @NotEmpty
    @Size(min = 16, max = 16)
    private String destination;

    @DecimalMin("0.01")
    private BigDecimal sum;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
