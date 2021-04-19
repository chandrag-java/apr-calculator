package com.apr.calc.loan;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Chandra Gudempati
 * 
 * This Test Class covers major controller method Testcases.
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private LoanService mockLoanService;
	private AutoCloseable closeable;
	
	@InjectMocks
	LoanController loanController;
	
	private Loan loan;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	
	@BeforeEach
	public void init() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
		
		loan = new Loan(200L,"Will Smith","111-11-1111",sdf.parse("09/25/1968"),Double.valueOf(500000),Double.valueOf(5),"mortgage",730);
		loan.setApr(Double.valueOf(5.15));
	}
	
	/**
     * This Test covers the controller getLoan() method
     * /loans/{id} - GET method
     * 
     * @param none
     * @return none
     *
     */
	@Test
	@DisplayName("GET /loans/{id}")
	final void getLoanIdTest() throws Exception {
		// Check the Service call invoking
		Mockito.when(mockLoanService.getLoan(loan.getLoanId())).thenReturn(loan);
		
		// Check the Loan Get Call
		mockMvc.perform(get("/loans/{id}",loan.getLoanId()))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       
		       // Check Loan JSON Object Data exists
               //.andExpect(model().hasNoErrors())
               .andExpect(jsonPath("$.name").exists())
               .andExpect(jsonPath("$.ssn").exists())
               .andExpect(jsonPath("$.dob").exists())
               .andExpect(jsonPath("$.loanAmt").exists())
               .andExpect(jsonPath("$.rate").exists())
               .andExpect(jsonPath("$.loanType").exists())
               .andExpect(jsonPath("$.loanTerm").exists())
               .andExpect(jsonPath("$.apr").exists());
               //Uncommenting following should fail since don't have apr is blank
			   //.andExpect(jsonPath("$.apr").isNotEmpty());
		
		Mockito.verify(mockLoanService).getLoan(loan.getLoanId());
		
	}  
	
	/**
     * This Test covers the controller addLoan() method
     * /loans - POST method
     * 
     * @param none
     * @return none
     *
     */
    @Test
	@DisplayName("POST /loans")
	final void addLoanIdTest() throws Exception {
    	
    	when(mockLoanService.isLoanProcessed(any(Loan.class))).thenReturn(true);
    	
    	mockMvc.perform(post("/loans")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(convertObjToJsonString(loan)))
    	        .andExpect(status().isCreated());
    	  
	}  
	
	@AfterEach
	void closeService() throws Exception {
        closeable.close();
    }
	
	// Helper Method
	public static String convertObjToJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
