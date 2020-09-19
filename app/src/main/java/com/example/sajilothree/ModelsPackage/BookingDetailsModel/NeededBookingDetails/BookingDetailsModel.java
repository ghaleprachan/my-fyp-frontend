
package com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetailsModel {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Customer")
    @Expose
    private List<Customer> customer = null;
    @SerializedName("ServiceProvider")
    @Expose
    private List<ServiceProvider> serviceProvider = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public List<ServiceProvider> getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(List<ServiceProvider> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}
