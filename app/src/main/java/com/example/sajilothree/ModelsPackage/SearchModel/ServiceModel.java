package com.example.sajilothree.ModelsPackage.SearchModel;


public class ServiceModel {
    private Integer serviceImage;
    private String serviceName;
    private float serviceRating;

    public ServiceModel(Integer serviceImage, String serviceName, float serviceRating) {
        this.serviceImage = serviceImage;
        this.serviceName = serviceName;
        this.serviceRating = serviceRating;
    }

    public Integer getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(Integer serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public float getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(float serviceRating) {
        this.serviceRating = serviceRating;
    }
}
