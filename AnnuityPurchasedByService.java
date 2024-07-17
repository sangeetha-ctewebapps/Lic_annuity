package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.AnnuityPurchasedBy;

public interface AnnuityPurchasedByService {

    // Modified method to support filtering the list of annuity purchases based on various criteria
    List<AnnuityPurchasedBy> getAllAnnuityPurchasedBy(String filterCriteria);

    // Modified method to support filtering the list of annuity purchases based on various criteria
    public AnnuityPurchasedBy getAnnuityPurchasedByById(long id);

    // Modified method to support advanced search options, flexible output formats, pagination, advanced filtering and sorting, integration with external systems, and audit trail/logging support
    public AnnuityPurchasedBy findByCode(String code, String searchOptions, String outputFormat, int pageNumber, int pageSize, String filter, String sort, String externalSystem, boolean auditTrail);

    // Modified method to fetch the annuity details by annuity name
    public AnnuityPurchasedBy getAnnuityPurchasedByByName(String name) {
        // Validate the annuity name format and check if it corresponds to an existing annuity record
        if (!isValidAnnuityName(name)) {
            // Return null if the annuity name is not valid
            return null;
        }

        // Call the method in AnnuityPurchasedByServiceImpl to fetch the details from the database
        return AnnuityPurchasedByServiceImpl.getAnnuityPurchasedByByName(name);
    }

    // Method to validate the annuity name format and check if it corresponds to an existing annuity record
    private boolean isValidAnnuityName(String name) {
        // Implement the validation logic here
        // ...
    }

    // Method to create an error response
    private AnnuityPurchasedBy createErrorResponse() {
        // Implement the error response creation logic here
        // ...
    }

}