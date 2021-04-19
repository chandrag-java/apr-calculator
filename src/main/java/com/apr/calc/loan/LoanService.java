package com.apr.calc.loan;

import java.util.List;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Chandra Gudempati
 *
 */
@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepo;
	
    /**
     * List All Loans : This method in not required for prod created only for debug purpose
     * 
     * @param none
     * @return List<Loan> all loans
     *
     */
    public List<Loan> getAllLoans() {
    	return loanRepo.findAll();
    }
    
    /**
     * Get Loan By Id
     *
     * @param id loanid
     * @return Loan loan object from DB
     */
    public Loan getLoan(Long id) {
    	return loanRepo.getOne(id);
    	// I want to use following approach but mokito throwing some exeception
    	// do time contraints using above Jpa method, need to do some research on
    	// how mockito behaving on <Optional> objects
    	//return loanRepo.findById(id).get();
    }
    
    /**
     * Persist the loan in DB
     * @param  loan      post request loan object
     * @return none
     *
     */
    public Loan addLoan(Loan loan) {
    	return loanRepo.save(loan);
    }
    
    /**
     * Update Loan in DB
     * 
     * @param id  loan id
     * @return none
     *
     */
    public void updateLoan(Loan loan,Long id) {
    	loanRepo.save(loan);
    }
    
    /**
     * Process the loan - If the loan processed and persisted successfully into the database
     *                    return true to controller
     * @param  loan      post request loan object
     * @return boolean	 if loan processed successfully without errors return true
     *
     */
    public boolean isLoanProcessed(Loan loan) {
    	// Validate the Loan
    	if(isValid(loan)) {
    		
    	  	// Get the Loan Fee
        	Double loanFees = getLoanFees(loan.getLoanType().trim());
        	
        	// Get the total Loan Interest
        	Double totLoanInterest = calcLoanTotalInterest(loan.getLoanAmt(),loan.getRate(),loan.getLoanTerm());
        	
        	// Get the APR
        	Double apr = getAPR(totLoanInterest,loanFees,loan.getLoanAmt(),loan.getLoanTerm());
        	
        	// Set calculate APR to Loan model
        	loan.setApr(apr);
        	
        	// Add the loan to db
        	this.addLoan(loan);
        	
    		return true;
    		
    	} else {
    		// If the Loan is not valid throw custom exception
    		// Still need to be implemented
    		return false;
    	}
    }
    
    /**
     * Calculate APR - based on APR formula returns apr
     * 
     * @param totInterest     	total term loan interest
     * @param fees				loan fees 
     * @loanAmt	loanAmt			loan principal
     * @termDays termDays		loan term in Days
     *
     */
    public Double getAPR(Double totInterest, Double fees, Double loanAmt, int termDays) {
        Double calcApr = (((((fees + totInterest) / loanAmt) / termDays) * 365) * 100); 
        Double roundedcalcApr = Precision.round(calcApr, 2);
    	return roundedcalcApr;
    }
    
    /**
     * Calculate Loan Term Interest - This method will calculate total term loan interest.
     * 
     * @param  loanAmout             pricipal loan amount
     * @Param  rate                  loan interest rate
     * @Param  termDays              loan term in days
     * @return roundedTotalInterest  total interest rounded to 2 digits
     * 
     */
    public Double calcLoanTotalInterest(Double loanAmt, Double rate, int termDays) {
    	// Convert into Loan Term into Years
    	int termYrs = termDays / 365;
    	// Calculate the Total Interest
    	Double totalInterest = (loanAmt*rate*termYrs)/100;
    	// Round the total interest with 2 digit precision
    	Double roundedTotalInterest = Precision.round(totalInterest, 2);
    	return roundedTotalInterest;
    }
    
    /**
     * Get the Loan Fees based on the Loan Type - 
     * 
     * @Param  loanType    type of loan (student,auto etc.,) 
     * @Return loanFees    loan fees associated with the loan type.
     * 
     */
    public Double getLoanFees(String loanType) {
    	Double loanFees = null;
    	 // Check whether loanType exists 
    	LoanType loanTypeEnum = LoanType.getIfPresent(loanType);
    	
		if(loanTypeEnum != null) {
			loanFees = loanTypeEnum.getLoanFees();
		} else {
			// Handle in custom error exception
			System.out.println(" Not a valid loan Type >> ");
		}
		return loanFees;
    }
    
    /**
     * Check post request body inputs are valid
     * Note : This method in not implemented main intension of this method is before 
     *        persist into the data base validate all loan values
     *
     */
    public boolean isValid(Loan loan) {
    	return true;
    }
    
    /**
     * Delete Loan : This method in not required for prod created only for debug purpose
     * 
     * @param id  loan id
     * @return none
     *
     */
    public void removeLoan(Long id) {
    	loanRepo.deleteById(id);
    }
    

}
