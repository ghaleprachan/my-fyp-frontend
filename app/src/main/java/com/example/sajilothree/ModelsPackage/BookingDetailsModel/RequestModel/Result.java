
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.RequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("BookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerUsername")
    @Expose
    private String customerUsername;
    @SerializedName("SpecialistId")
    @Expose
    private Integer specialistId;
    @SerializedName("SpecialistUsername")
    @Expose
    private String specialistUsername;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("SpecialistName")
    @Expose
    private String specialistName;
    @SerializedName("CustomerImage")
    @Expose
    private String customerImage;
    @SerializedName("ServiceDate")
    @Expose
    private String serviceDate;
    @SerializedName("ServiceType")
    @Expose
    private String serviceType;
    @SerializedName("ProblemDescription")
    @Expose
    private String problemDescription;
    @SerializedName("ProblemImage")
    @Expose
    private Object problemImage;
    @SerializedName("SpecialistAcceptance")
    @Expose
    private String specialistAcceptance;
    @SerializedName("BookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("SpecialistImage")
    @Expose
    private String SpecialistImage;
    @SerializedName("ServiceAddress")
    @Expose
    private String ServiceAddress;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Integer getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Integer specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistUsername() {
        return specialistUsername;
    }

    public void setSpecialistUsername(String specialistUsername) {
        this.specialistUsername = specialistUsername;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getSpecialistImage() {
        return SpecialistImage;
    }

    public void setSpecialistImage(String specialistImage) {
        SpecialistImage = specialistImage;
    }

    public String getServiceAddress() {
        return ServiceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        ServiceAddress = serviceAddress;
    }
}
