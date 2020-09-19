
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.UserStates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("Completed")
    @Expose
    private Double completed;
    @SerializedName("OnBudget")
    @Expose
    private Double onBudget;
    @SerializedName("OnTime")
    @Expose
    private Double onTime;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Double getCompleted() {
        return completed;
    }

    public void setCompleted(Double completed) {
        this.completed = completed;
    }

    public Double getOnBudget() {
        return onBudget;
    }

    public void setOnBudget(Double onBudget) {
        this.onBudget = onBudget;
    }

    public Double getOnTime() {
        return onTime;
    }

    public void setOnTime(Double onTime) {
        this.onTime = onTime;
    }

}
