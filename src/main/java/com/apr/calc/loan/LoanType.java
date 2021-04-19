package com.apr.calc.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Enums;

/**
 * @author Chandra Gudempati
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LoanType {
	STUDENT("student", 0.00),
	AUTO("auto", 500.00),
	PERSONAL("personal", 750.00),
	MORTGAGE("mortgage", 1500.00);
	
	private final String loanName;
	private final Double loanFees;
	
	private LoanType(String loanName, Double loanFees) {
		this.loanName = loanName;
		this.loanFees = loanFees;
	}
	
	public String getLoanName() {
		return loanName;
	}
	
	public Double getLoanFees() {
		return loanFees;
	}

	public static LoanType getIfPresent(String name) {
		if (Enums.getIfPresent(LoanType.class, name.toUpperCase()) != null &&
			!LoanType.valueOf(name.toUpperCase()).getLoanName().trim().equals(name.trim())) {
				return null;
		}
		return LoanType.valueOf(name.toUpperCase());
    }
 } 