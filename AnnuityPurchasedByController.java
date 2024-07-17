package com.lic.epgs.mst.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.AnnuityPurchasedBy;
import com.lic.epgs.mst.exceptionhandling.AnnuityPurchasedByServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AnnuityPurchasedByService;
import com.lic.epgs.mst.service.FuzzyMatchingService; // Import the fuzzy matching service

@RestController
public class AnnuityPurchasedByController {

	private static final Logger logger = LoggerFactory.getLogger(AnnuityPurchasedByController.class);

	@Autowired
	private AnnuityPurchasedByService annuitypurchasedbyService;
	
	@Autowired
	private FuzzyMatchingService fuzzyMatchingService; // Autowire the fuzzy matching service

	String className = this.getClass().getSimpleName();

	@GetMapping("/annuitypurchased")
	public List<AnnuityPurchasedBy> getAllAnnuityPurchasedBy(@RequestBody Map<String, String> filterCriteria) throws ResourceNotFoundException, AnnuityPurchasedByServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<AnnuityPurchasedBy> annuitypurchasedby = annuitypurchasedbyService.getAllAnnuityPurchasedBy(filterCriteria);

			if (annuitypurchasedby.isEmpty()) {
				logger.debug("inside Annuity Purchased By controller getAllAnnuityPurchasedBy() method");
				logger.info("AnnuityPurchasedBy list is empty ");
				throw new ResourceNotFoundException("AnnuityPurchasedBy not found");
			}
			return annuitypurchasedby;
		} catch (Exception ex) {
			logger.error(" get All Annuity Purchased By  exception occured." + ex.getMessage());
			throw new AnnuityPurchasedByServiceException("internal server error");
		}
	}

	@GetMapping("/annuitypurchased/{id}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		// Validate annuity ID before proceeding with the retrieval process
		if (!isValidAnnuityId(id)) {
			logger.error("Invalid annuity ID: " + id);
			return ResponseEntity.badRequest().build();
		}

		try {
			AnnuityPurchasedBy annuityPurchasedBy = annuitypurchasedbyService.getAnnuityPurchasedByById(id);
			if (annuityPurchasedBy == null) {
				throw new ResourceNotFoundException("AnnuityPurchasedBy not found");
			}
			return ResponseEntity.ok().body(annuityPurchasedBy);
		} catch (Exception ex) {
			logger.error("An error occurred while retrieving AnnuityPurchasedBy: " + ex.getMessage());
			throw new ResourceNotFoundException("An error occurred while retrieving AnnuityPurchasedBy");
		}
	}

	@GetMapping("/annuitypurchasedByCode/{code}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		// Validate the format of the provided code before attempting to retrieve the annuity details
		if (!isValidCodeFormat(code)) {
			logger.error("Invalid code format: " + code);
			return ResponseEntity.badRequest().body("Invalid code format: " + code);
		}

		// Add advanced search options, flexible output formats, pagination, advanced filtering and sorting here
		// Integration with external systems
		// Add audit trail/logging support here

		return ResponseEntity.ok().body(annuitypurchasedbyService.findByCode(code));
	}
	
	@GetMapping("/annuitypurchasedByName/{name}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByByName(@PathVariable String name) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + name);

		// Add advanced search options, flexible output formats, pagination, advanced filtering and sorting here
		// Integration with external systems
		// Add audit trail/logging support here

		return ResponseEntity.ok().body(annuitypurchasedbyService.findByName(name));
	}
	
	@PostMapping("/annuitypurchased/search")
	public List<AnnuityPurchasedBy> searchAnnuityPurchasedBy(@RequestBody SearchCriteria criteria) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + criteria);

		// Use the fuzzy matching service to perform fuzzy matching on the policyholder's name
		String fuzzyPolicyholderName = fuzzyMatchingService.performFuzzyMatching(criteria.getPolicyholderName());
		criteria.setPolicyholderName(fuzzyPolicyholderName);

		return annuitypurchasedbyService.searchAnnuityPurchasedBy(criteria);
	}
	
	@GetMapping("/annuitypurchasedByName/{name}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByByName(@PathVariable String name) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + name);

		try {
			AnnuityPurchasedBy annuityPurchasedBy = annuitypurchasedbyService.getAnnuityPurchasedByByName(name);
			if (annuityPurchasedBy == null) {
				throw new ResourceNotFoundException("AnnuityPurchasedBy not found");
			}
			return ResponseEntity.ok().body(annuityPurchasedBy);
		} catch (Exception ex) {
			logger.error("An error occurred while retrieving AnnuityPurchasedBy: " + ex.getMessage());
			throw new ResourceNotFoundException("An error occurred while retrieving AnnuityPurchasedBy");
		}
	}

	// Method to validate the annuity ID
	private boolean isValidAnnuityId(long id) {
		// Add validation logic here to check if the annuity ID is in the correct format and corresponds to an existing annuity record
		// Return true if the annuity ID is valid, otherwise return false
		return true; // Placeholder, replace with actual validation logic
	}
	
	// Method to validate the code format
	private boolean isValidCodeFormat(String code) {
		// Add validation logic here to check if the code meets the required format criteria
		// Return true if the code format is valid, otherwise return false
		return true; // Placeholder, replace with actual validation logic
	}
}

class SearchCriteria {
	private String contractNumber;
	private String policyholderName;
	private String annuityType;

	// getters and setters

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getPolicyholderName() {
		return policyholderName;
	}

	public void setPolicyholderName(String policyholderName) {
		this.policyholderName = policyholderName;
	}

	public String getAnnuityType() {
		return annuityType;
	}

	public void setAnnuityType(String annuityType) {
		this.annuityType = annuityType;
	}
}

class FuzzyMatchingService {
	// Add the fuzzy matching algorithm here
	public String performFuzzyMatching(String input) {
		// Implement the fuzzy matching algorithm using an existing library
		// Return the modified input string after performing fuzzy matching
		return input; // Placeholder, replace with actual fuzzy matching algorithm
	}
}