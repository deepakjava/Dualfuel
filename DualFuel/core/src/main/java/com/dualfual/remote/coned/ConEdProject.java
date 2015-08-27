package com.dualfual.remote.coned;

public class ConEdProject {

    private String serviceId;
    private String serviceAddress;
    private String requestType;

    private String utilityType;
    private String customerRep;
    private String dateSubmitted;
    private String status;

    private byte[] originalRequest;
    private byte[] updatedRequest;

    private String oilConsumptionUnit;
    private Double originalOilConsumption;
    private Double updatedOilConsumption;
    private String electricHeat = null;

    private ConEdStatus completedStatus;
    private ConEdStatus notCompletedStatus;


    private String scopeOFWork;

    private int id;

    public ConEdProject(String serviceId, String serviceAddress, String requestType) {
        this.serviceId = serviceId;
        this.serviceAddress = serviceAddress;
        this.requestType = requestType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getUtilityType() {
        return utilityType;
    }

    public void setUtilityType(String utilityType) {
        this.utilityType = utilityType;
    }

    public String getCustomerRep() {
        return customerRep;
    }

    public void setCustomerRep(String customerRep) {
        this.customerRep = customerRep;
    }

    public byte[] getOriginalRequest() {
        return originalRequest;
    }

    public void setOriginalRequest(byte[] originalRequest) {
        this.originalRequest = originalRequest;
    }

    public byte[] getUpdatedRequest() {
        return updatedRequest;
    }

    public void setUpdatedRequest(byte[] updatedRequest) {
        this.updatedRequest = updatedRequest;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOilConsumptionUnit() {
        return oilConsumptionUnit;
    }

    public void setOilConsumptionUnit(String oilConsumptionUnit) {
        this.oilConsumptionUnit = oilConsumptionUnit;
    }

    public Double getOriginalOilConsumption() {
        return originalOilConsumption;
    }

    public void setOriginalOilConsumption(Double originalOilConsumption) {
        this.originalOilConsumption = originalOilConsumption;
    }

    public Double getUpdatedOilConsumption() {
        return updatedOilConsumption;
    }

    public void setUpdatedOilConsumption(Double updatedOilConsumption) {
        this.updatedOilConsumption = updatedOilConsumption;
    }

    public String getScopeOFWork() {
        return scopeOFWork;
    }

    public void setScopeOFWork(String scopeOFWork) {
        this.scopeOFWork = scopeOFWork;
    }

    public ConEdStatus getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(ConEdStatus completedStatus) {
        this.completedStatus = completedStatus;
    }

    public ConEdStatus getNotCompletedStatus() {
        return notCompletedStatus;
    }

    public void setNotCompletedStatus(ConEdStatus notCompletedStatus) {
        this.notCompletedStatus = notCompletedStatus;
    }

    public String getElectricHeat() {
        return electricHeat;
    }

    public void setElectricHeat(String electricHeat) {
        this.electricHeat = electricHeat;
    }
}
