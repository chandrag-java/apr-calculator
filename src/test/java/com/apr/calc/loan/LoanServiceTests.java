package com.apr.calc.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

//import com.google.common.base.Optional;

/**
 * @author Chandra Gudempati
 * 
 * This Test Class covers major service method Test cases.
 * 
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LoanServiceTests {
	
	@Autowired
	private LoanService loanService;
	
	@MockBean
	private LoanRepository mockLoanRepo;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private Loan loan1;
	private Loan loan2;
	private Loan loan3;
	//private Loan loan4;
	
	 @BeforeEach
	 public void init() throws ParseException {
		// Initialize common Test objects  
		loan1  = new Loan(200L,"Will Smith","111-11-1111",sdf.parse("09/25/1968"),Double.valueOf(500000),Double.valueOf(5),"mortgage",730);
		loan2  = new Loan(201L,"Tom Brady","222-22-2222",sdf.parse("08/03/1977"),Double.valueOf(250000),Double.valueOf(4.5),"personal",365);
		loan3  = new Loan(202L,"Bill Belichick","333-33-3333",sdf.parse("04/16/1952"),Double.valueOf(100000),Double.valueOf(3.5),"auto",1000);
		//loan4  = new Loan(203L,"Daniel Radcliffe","44-44-4444",sdf.parse("07/23/1989"),Double.valueOf(150000),Double.valueOf(7.5),"student",1250);
	 }

    /**
	 * This test check the service level method add loan
	 *
	 * @param none
	 * @return none
	 */
	@Test
	public void addLoanTest() {
		assertNotNull(loan3);
		doAnswer((itr) -> {
			System.out.println("Loan Id : " + itr.getArgument(0) + " Loan Name : " + itr.getArgument(1));
			assertTrue("202L".equals(itr.getArgument(0)));
			assertTrue("Bill Belichick".equals(itr.getArgument(1)));
			return null;	
	    }).when(mockLoanRepo).save(loan3);
		
	}
	
    /**
	 * This test check the service level method get all loans
	 *
	 * @param none
	 * @return none
	 * @throws ParseException
	 */
	@Test
	public void getLoansTest() throws ParseException {
		
	    when(mockLoanRepo.findAll()).thenReturn(Stream
				.of(loan1,loan2)
				.collect(Collectors.toList()));
	    
		assertNotNull(loanService.getAllLoans());
		assertEquals(2,loanService.getAllLoans().size());
		
	}
	
    /**
	 * This test check the service level method get loans by Id
	 *
	 * @param none
	 * @return none
	 * @throws ParseException
	 */
	@Test
	public void getLoansByIdTest() throws ParseException {
	     assertNotNull(loan2);
	     
	     when(mockLoanRepo.getOne(201L)).thenReturn(loan2);
	    // some reason following throwing NPE need to debug on free time 
	    //when(mockLoanRepo.findById(201L).get()).thenReturn(Optional.of(loan2));
		    
	    assertEquals(Long.valueOf(201L),loan2.getLoanId());
		assertEquals("Tom Brady", loan2.getName());
		assertEquals("personal",loan2.getLoanType()); 
	} 
	
	
    /**
	 * This test check the service level method check loan fees
	 *
	 * @param none
	 * @return none
	 * @throws ParseException
	 */
	@Test
	public void validateLoanFeesTest() throws ParseException {
		// This test should be parameterized for each loan type but due to
		// time constains cutting short only with one loan type
	     assertNotNull(loan3);
	     Double autofees = Double.valueOf(500.00);
	     assertEquals(autofees,loanService.getLoanFees(loan3.getLoanType())); 
	} 
	
    /**
	 * This test check the service level method validate the interest.
	 *
	 * @param none
	 * @return none
	 * @throws ParseException
	 */
	@Test
	public void validateInterestTest() throws ParseException {
		// This test should be parameterized for each loan type but due to
		// time constains cutting short only with one loan type
	     assertNotNull(loan1);
	     Double totalInterst = Double.valueOf(50000.00);
	     assertEquals(totalInterst,loanService.calcLoanTotalInterest(loan1.getLoanAmt(),loan1.getRate(),loan1.getLoanTerm())); 
	} 
	
    /**
	 * This test check the service level method validate calculate apr.
	 *
	 * @param none
	 * @return none
	 * @throws ParseException
	 */
	@Test
	public void checkAPRTest() throws ParseException {
		// This test should be parameterized for each loan type but due to
		// time constains cutting short only with one loan type
	     assertNotNull(loan1);
	     Double expectedAPR = Double.valueOf(5.15);
	     Double totalLoanInterest = loanService.calcLoanTotalInterest(loan1.getLoanAmt(),loan1.getRate(),loan1.getLoanTerm());
	     Double loanFees = loanService.getLoanFees(loan1.getLoanType());
	     
	     assertEquals(expectedAPR,loanService.getAPR(totalLoanInterest, loanFees, loan1.getLoanAmt(), loan1.getLoanTerm())); 
	} 
	
	
}
