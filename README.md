# apr-calculator
spring boot app service calculate apr

End Point URL’s :

GET	    /loans/list	
GET 	  /loans/{id}	
POST	  /loans	
PUT     /loans/{id}	
DELETE	/loans/{id}	

POST AND PUT JSON Request :
{
    "loanId": 108, 
    "name": "Will Smith",
    "ssn": "111-11-1111",
    "dob": "09/25/1968",
    "loanAmt": 500000.0,
    "rate": 5.0,
    "loanType": "mortgage",
    "loanTerm": 730,
    "loanStatus": "A",
    "updatedBy": "Web User"
}

GET RESPONSE :
{
    "loanId": 108,
    "name": "Will Smith",
    "ssn": "111-11-1111",
    "dob": "09/25/1968",
    "loanAmt": 500000.0,
    "rate": 5.0,
    "loanType": "mortgage",
    "loanTerm": 730,
    "apr": 5.15,
    "loanStatus": "A",
    "updatedBy": "Web User",
    "insertedDate": "2021-04-19T00:24:17.3544372",
    "updatedDate": "2021-04-19T00:24:17.3574228"
}


