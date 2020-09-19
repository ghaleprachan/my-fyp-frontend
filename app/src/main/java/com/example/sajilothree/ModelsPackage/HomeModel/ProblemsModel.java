package com.example.sajilothree.ModelsPackage.HomeModel;

public class ProblemsModel {
    private Integer profilePhoto, offerPhoto;
    private String name, description, descount_amount;

    public ProblemsModel(Integer profilePhoto, Integer offerPhoto,
                         String name, String description, String descount_amount) {
        this.profilePhoto = profilePhoto;
        this.offerPhoto = offerPhoto;
        this.name = name;
        this.description = description;
        this.descount_amount = descount_amount;
    }

    public Integer getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Integer profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Integer getOfferPhoto() {
        return offerPhoto;
    }

    public void setOfferPhoto(Integer offerPhoto) {
        this.offerPhoto = offerPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescount_amount() {
        return descount_amount;
    }

    public void setDescount_amount(String descount_amount) {
        this.descount_amount = descount_amount;
    }
}
