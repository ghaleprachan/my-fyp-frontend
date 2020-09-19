
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.BookingResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("BookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("SpecialistName")
    @Expose
    private String specialistName;
    @SerializedName("SpecialistId")
    @Expose
    private Integer specialistId;
    @SerializedName("SpecialistUsername")
    @Expose
    private String specialistUsername;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("CustomerUsername")
    @Expose
    private String customerUsername;

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

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
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

}