package com.apr.calc.loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Chandra Gudempati
 *
 */
public interface LoanRepository extends JpaRepository<Loan,Long> {
	
	// Following custom queries not implemented, took only JPA out of box methods
	//if the client get loans based on name and ssn
	// In the requirement only says name, it would have better split first and last name
    @Query("SELECT ln FROM Loan ln  WHERE ln.name=(:name) AND ln.ssn= (:ssn)")
    List<Loan> findLoansByNameAndSsn(@Param("name") String name, @Param("ssn") String ssn);
    
    @Query("SELECT ln FROM Loan ln  WHERE ln.name=(:name)")
    List<Loan> findLoansByName(@Param("name") String name);

}
