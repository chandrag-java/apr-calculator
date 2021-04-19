package com.apr.calc.loan;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apr.calc.AprCalculatorApplication;

/**
 * @author Chandra Gudempati
 * 
 * This Test Class covers major integration testcases application level
 */
@SpringBootTest(classes=AprCalculatorApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTests {
	
	@LocalServerPort
	private int localport;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
     * This Test covers the scenario RESTful api call for the end point
     * /loans/{loanid} - GET method
     * 
     * @param none
     * @return none
     *
     */
	@Test
	public void integration_GetLoanId_Test() throws Exception {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				    createPortURL("/loans/108"),
					HttpMethod.GET, entity, String.class);

		String expected = "{\"loanId\": 108, " +
							    "\"name\": \"Will Smith\"," +
							   " \"ssn\": \"111-11-1111\"," +
							    "\"dob\": \"09/25/1968\","  +
							    "\"loanAmt\": 500000.0,"    +
							    "\"rate\": 5.0,"            +
							    "\"loanType\": \"mortgage\"," +
							    "\"loanTerm\": 730," +
							    "\"apr\": 5.15," +
							    "\"loanStatus\": \"A\"," +
							    "\"updatedBy\": \"Web User\"," +
							    "\"insertedDate\": \"2021-04-19T00:24:17.3544372\"," +
							    "\"updatedDate\": \"2021-04-19T00:24:17.3574228\"" +
		                       "}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	} 
	
	/**
     * This Test covers the scenario RESTful api call for the end point
     * /loans - POST method
     * 
     * @param none
     * @return none
     *
     */
	@Test
	public void integration_Add_NewLoan_Test() throws Exception {

		Loan loan = new Loan(201L,"Tom Brady","222-22-2222",sdf.parse("08/03/1977"),Double.valueOf(250000),Double.valueOf(4.5),"personal",365);

		HttpEntity<Loan> entity = new HttpEntity<Loan>(loan, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createPortURL("/loans"),
				HttpMethod.POST, entity, String.class);

		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));

	}

	
	// Helper method
	private String createPortURL(String uri) {
		return "http://localhost:" + localport + uri;
	}

}
