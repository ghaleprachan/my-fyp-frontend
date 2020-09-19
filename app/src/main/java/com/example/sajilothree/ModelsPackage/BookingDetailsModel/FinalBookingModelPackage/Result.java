
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.FinalBookingModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("BookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("SpecialistId")
    @Expose
    private Integer specialistId;
    @SerializedName("SpecialistName")
    @Expose
    private String specialistName;
    @SerializedName("SpecialistImage")
    @Expose
    private Object specialistImage;
    @SerializedName("SpecialistRating")
    @Expose
    private Double specialistRating;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerImage")
    @Expose
    private String customerImage;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("ServiceDate")
    @Expose
    private String serviceDate;
    @SerializedName("BookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("CustomerAddress")
    @Expose
    private String customerAddress;
    @SerializedName("ProblemDescription")
    @Expose
    private String problemDescription;
    @SerializedName("ProblemImage")
    @Expose
    private Object problemImage;
    @SerializedName("SpecialistAcceptance")
    @Expose
    private String specialistAcceptance;
    @SerializedName("CompletedStatus")
    @Expose
    private String completedStatus;
    @SerializedName("BillId")
    @Expose
    private Integer billId;
    @SerializedName("ServiceCharge")
    @Expose
    private Double serviceCharge;
    @SerializedName("TravellingCost")
    @Expose
    private Double travellingCost;
    @SerializedName("Discount")
    @Expose
    private Double discount;
    @SerializedName("TotalCharge")
    @Expose
    private Double totalCharge;
    @SerializedName("CustomerAcceptance")
    @Expose
    private String customerAcceptance;
    @SerializedName("PaidStatus")
    @Expose
    private String paidStatus;
    @SerializedName("IssuedDate")
    @Expose
    private String issuedDate;

    @SerializedName("SpecialistUserName")
    @Expose
    private String SpecialistUserName;

    @SerializedName("CustomerUserName")
    @Expose
    private String CustomerUserName;

    public String getSpecialistUserName() {
        return SpecialistUserName;
    }

    public void setSpecialistUserName(String specialistUserName) {
        SpecialistUserName = specialistUserName;
    }

    public String getCustomerUserName() {
        return CustomerUserName;
    }

    public void setCustomerUserName(String customerUserName) {
        CustomerUserName = customerUserName;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public Object getSpecialistImage() {
        return specialistImage;
    }

    public void setSpecialistImage(Object specialistImage) {
        this.specialistImage = specialistImage;
    }

    public Double getSpecialistRating() {
        return specialistRating;
    }

    public void setSpecialistRating(Double specialistRating) {
        this.specialistRating = specialistRating;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Object getProblemImage() {
        return problemImage;
    }

    public void setProblemImage(Object problemImage) {
        this.problemImage = problemImage;
    }

    public String getSpecialistAcceptance() {
        return specialistAcceptance;
    }

    public void setSpecialistAcceptance(String specialistAcceptance) {
        this.specialistAcceptance = specialistAcceptance;
    }

    public String getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(String completedStatus) {
        this.completedStatus = completedStatus;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getTravellingCost() {
        return travellingCost;
    }

    public void setTravellingCost(Double travellingCost) {
        this.travellingCost = travellingCost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getCustomerAcceptance() {
        return customerAcceptance;
    }

    public void setCustomerAcceptance(String customerAcceptance) {
        this.customerAcceptance = customerAcceptance;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

}
