package com.example.sajilothree.ServicesPackage.UpdateProfile;

import com.example.sajilothree.AdapterPackages.EditProfileAdapters.EditAddressAdapter;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.Contact;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.Address;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.Email;
import com.example.sajilothree.ModelsPackage.UpdateProfile.UpdatableDetails.UpdateProfileDetailsModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileService {
    public static ArrayList<UpdateProfileDetailsModel> editProfileDetails = new ArrayList<>(0);

    public static boolean addDetails(UpdateProfileDetailsModel detailsModel) {
        editProfileDetails.clear();
        editProfileDetails.add(0, detailsModel);
        return true;
    }

    //    These are all the updated details into their respective arrays:
    public static ArrayList<Contact> newContacts = new ArrayList<>();
    public static ArrayList<Email> newEmails = new ArrayList<>();
    public static ArrayList<Address> newAddresses = new ArrayList<>();

    public static void addAllContact(List<Contact> contact) {
        newContacts.clear();
        newContacts.addAll(contact);
    }

    public static void addAllEmails(List<Email> email) {
        newEmails.clear();
        newEmails.addAll(email);
    }

    public static void addAllAddress(List<Address> address) {
        newAddresses.clear();
        newAddresses.addAll(address);
    }

    public static boolean addContact(int position, String contact) {
        if (position >= 0) {
            newContacts.get(position).setContactNumber(contact);
            return true;
        } else {
            Contact newContact = new Contact();
            newContact.setContactId(-1);
            newContact.setContactNumber(contact);
            newContacts.add(newContact);
            return true;
        }
    }

    public static boolean addEmails(int position, String email) {
        if (position >= 0) {
            newEmails.get(position).setEmail1(email);
            return true;
        } else {
            Email newEmail = new Email();
            newEmail.setEmailId(-1);
            newEmail.setEmail1(email);
            newEmails.add(newEmail);
            return true;
        }
    }

    public static boolean addAddress(int position, String district, String municipality, String currentLocation) {
        if (position >= 0) {
            /*newAddresses.get(position).setDistrictName(district);
            newAddresses.get(position).setMunicipalityName(municipality);
            newAddresses.get(position).setCurrentLocation(currentLocation);*/
            Address updateAddress = newAddresses.get(position);
            updateAddress.setDistrictName(district);
            updateAddress.setMunicipalityName(municipality);
            updateAddress.setCurrentLocation(currentLocation);
            newAddresses.set(position, updateAddress);
            return true;
        } else {
            Address address = new Address();
            address.setAddressId(-1);
            address.setMunicipalityName(municipality);
            address.setDistrictName(district);
            address.setCurrentLocation(currentLocation);
            newAddresses.add(address);
            return true;
        }
    }
}
