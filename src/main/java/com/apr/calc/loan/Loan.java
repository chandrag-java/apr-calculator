package com.apr.calc.loan;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Chandra Gudempati
 *
 */
@Entity
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name="loan")
public class Loan {
	
	@Id
	@GeneratedValue
	@Column(nullable = false,name="Loan_Id")
	private Long loanId;   // Loan Id which is primary key
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String name;  // Loan holder name
	
	@Basic(optional = false)
	@Column(nullable = false)
	private String ssn; 

	@Basic(optional = false)
	@Column(nullable = false)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date dob;
	
	@Basic(optional = false)
	@Column(nullable = false,name="Loan_Amt")
	private Double loanAmt;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private Double rate;
	
	@Basic(optional = false)
	@Column(nullable = false,name="Loan_Type")
    private String loanType;
	
	@Basic(optional = false)
	@Column(nullable = false,name="Loan_Term")
	private int loanTerm;
	
	@Column(nullable = false)
	private Double apr;       // Calculated value
	
	@Column(nullable = false, name="Loan_Status", columnDefinition="Varchar(2)")
	private String loanStatus = new String("A");   // Loan Status 'A' for active, 'C' for closed etc.,

	private String updatedBy; // Last updated User
	
	@CreationTimestamp
	private LocalDateTime insertedDate; // Inserted Date

	@UpdateTimestamp
	private LocalDateTime updatedDate;  // Last Updated Date
	
	public Loan() {
    }
	
    public Loan(Long loanId, String name, String ssn, Date dob, Double loanAmt, Double rate, String loanType, int loanTerm) {
        this.loanId = loanId;
    	this.name = name;
        this.ssn = ssn;
        this.dob = dob;
        this.loanAmt = loanAmt;
        this.rate = rate;
        this.loanType = loanType;
        this.loanTerm = loanTerm;
    }
	
    public Long getLoanId() {
		return loanId;
	}
    
	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}
	
    public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public Double getLoanAmt() {
		return loanAmt;
	}
	
	public void setLoanAmt(Double loanAmt) {
		this.loanAmt = loanAmt;
	}
	
	public Double getRate() {
		return rate;
	}
	
	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	public String getLoanType() {
		return loanType;
	}
	
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	
	public int getLoanTerm() {
		return loanTerm;
	}
	
	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}
	
	public Double getApr() {
		return apr;
	}
	
	public void setApr(Double apr) {
		this.apr = apr;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	public LocalDateTime getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(LocalDateTime insertedDate) {
		this.insertedDate = insertedDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
