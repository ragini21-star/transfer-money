package com.dws.challenge.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ValidateAmount {
    public boolean checkIfAmountNegative(BigDecimal amount) {
    	if(amount.compareTo(BigDecimal.ZERO) < 0) {
    		return true;
    	}
    	return false;
    }
}
