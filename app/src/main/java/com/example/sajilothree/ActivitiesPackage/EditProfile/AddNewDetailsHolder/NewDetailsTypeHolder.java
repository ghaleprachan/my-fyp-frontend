package com.example.sajilothree.ActivitiesPackage.EditProfile.AddNewDetailsHolder;


//This is to hold what user wants to add i.e. address, contact or may be new emails
public class NewDetailsTypeHolder {
    public static String addNew;

    public static boolean setAddNew(String type) {
        addNew = type;
        return true;
    }


    public static int position;

    public static boolean addNewPosition(int newPosition) {
        position = newPosition;
        return true;
    }
/*
    public static String personName;
    public static Uri userPhoto;

    public static boolean addName(String newName) {
        personName = newName;
        return true;
    }

    public static boolean addImage(Uri newImage) {
        userPhoto = newImage;
        return true;
    }*/

}
