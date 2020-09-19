package com.example.sajilothree.ModelsPackage.ChatSystemPutLastMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLastMessageModel {

@SerializedName("$id")
@Expose
private String $id;
@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("message")
@Expose
private String message;
@SerializedName("result")
@Expose
private Integer result;

public String get$id() {
return $id;
}

public void set$id(String $id) {
this.$id = $id;
}

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getResult() {
return result;
}

public void setResult(Integer result) {
this.result = result;
}

}