package com.example.sajilothree.ModelsPackage.SPListSharePropertyModel;

public class ShareModel {
    private String professionImage;
    private String heading;

    public ShareModel(String professionImage, String heading) {
        this.professionImage = professionImage;
        this.heading = heading;
    }

    public String getProfessionImage() {
        return professionImage;
    }

    public String getHeading() {
        return heading;
    }
}
