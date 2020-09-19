
package com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileAbout;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("AboutUser")
    @Expose
    private String aboutUser;
    @SerializedName("UserProfessions")
    @Expose
    private List<UserProfession> userProfessions = null;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public List<UserProfession> getUserProfessions() {
        return userProfessions;
    }

    public void setUserProfessions(List<UserProfession> userProfessions) {
        this.userProfessions = userProfessions;
    }

}
