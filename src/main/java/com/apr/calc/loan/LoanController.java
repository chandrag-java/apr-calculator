
package com.apr.calc.loan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chandra Gudempati
 *
 */

@RestController
public class LoanController {
	
	@Autowired 
	private LoanService loanService;
		
	@GetMapping("/loans/{id}")
	public Loan getLoan(@PathVariable Long id) {
		return loanService.getLoan(id);
	}	
	
	@PostMapping("/loans")
	public ResponseEntity <String> addLoan(@RequestBody Loan loan) {
		
		if(loanService.isLoanProcessed(loan)) {
			return ResponseEntity.status(HttpStatus.CREATED).build(); //return http 201 return code
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build(); // return http 409 return code
		
	}	
	
	// Following two request URL's are only for debugging purpose
	@GetMapping("/loans/list")
	public List<Loan> getAllLoans() {
		return loanService.getAllLoans();
	}
	
	@PutMapping("/loans/{id}")
	public void updateLoan(@RequestBody Loan loan, @PathVariable Long id) {
		loanService.updateLoan(loan,id);
	}	
	
	@DeleteMapping("/loans/{id}")
	public void deleteLoan(@PathVariable Long id) {
		loanService.removeLoan(id);
	}	

}
