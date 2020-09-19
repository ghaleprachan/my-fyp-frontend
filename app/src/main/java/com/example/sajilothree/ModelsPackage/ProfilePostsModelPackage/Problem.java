
package com.example.sajilothree.ModelsPackage.ProfilePostsModelPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Problem {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ProblemId")
    @Expose
    private Integer problemId;
    @SerializedName("ProblemDescription")
    @Expose
    private String problemDescription;
    @SerializedName("ProblemImage")
    @Expose
    private String problemImage;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemImage() {
        return problemImage;
    }

    public void setProblemImage(String problemImage) {
        this.problemImage = problemImage;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

}
