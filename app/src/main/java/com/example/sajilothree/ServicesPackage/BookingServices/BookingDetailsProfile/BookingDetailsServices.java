package com.example.sajilothree.ServicesPackage.BookingServices.BookingDetailsProfile;

import com.example.sajilothree.ModelsPackage.BookingDetailsModel.NeededBookingDetails.BookingDetailsModel;

import java.util.ArrayList;

public class BookingDetailsServices {
    public static ArrayList<BookingDetailsModel> bookingDetails = new ArrayList<>(0);

    public static boolean addBookingModel(BookingDetailsModel model) {
        bookingDetails.clear();
        bookingDetails.add(0, model);
        return true;
    }

    public static ArrayList<String> getServices() {
        ArrayList<String> servicesList = new ArrayList<>();
        for (int i = 0; i < bookingDetails.get(0).getServiceProvider().get(0).getProfessions().size(); i++) {
            servicesList.add(bookingDetails.get(0).getServiceProvider().get(0).getProfessions().get(i).getProfessionName());
        }
        return servicesList;
    }

    public static String getAddresses() {
        String addresses;
        addresses = (bookingDetails.get(0).getCustomer().get(0).getAddresses().get(0).getDistrictName() + " - " +
                bookingDetails.get(0).getCustomer().get(0).getAddresses().get(0).getMunicipalityName() + ", " +
                bookingDetails.get(0).getCustomer().get(0).getAddresses().get(0).getCurrentLocation());
        return addresses;
    }

    public static ArrayList<String> addresses() {
        ArrayList<String> addresses = new ArrayList<>();
        for (int i = 0; i < BookingDetailsServices.bookingDetails.get(0).getCustomer().get(0).getAddresses().size(); i++) {
            addresses.add(bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getDistrictName() + "-" +
                    bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getMunicipalityName() + "," +
                    bookingDetails.get(0).getCustomer().get(0).getAddresses().get(i).getCurrentLocation());
        }
        return addresses;
    }
}
