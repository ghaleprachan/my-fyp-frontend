package com.example.sajilothree.APIPackage.BaseUrlPackage;

public class BaseURL {
    public static String BaseURL = "http://39f84380c395.ngrok.io/";

    public static String getOffer = "api/offer";
    public static String getProblem = "api/problem";
    public static String search = "api/search/search/";

    public static String searchServices = BaseURL + "api/search/getservices";

    public static String validateUser = BaseURL + "api/login";

    public static String headerURL = "api/user/GetUserHeader/";

    public static String profileDetailsURL = "api/user/getuserdetails/";

    public static String getUserFeedBacks = "api/user/GetUserfeedbacks/";

    public static String basicUserDetails = "api/user/GetUserBasicDetails/";

    public static String getProfilePosts = "api/user/getuserposts/";

    public static String getProfileAbout = BaseURL + "api/user/GetUserAbout";

    public static String getUserStates = BaseURL + "api/UserStates/GetUserStates?username=";

    public static String postOffers = "api/offer";

    public static String postProblems = "api/problem";

    public static String getCities = "api/search/getcities";

    public static String getRecommendedServices = "api/search/getserviceproviders";

    public static String getSPList = "api/search/GetSPList/";

    public static String getChat = BaseURL + "api/chats/";

    public static String postChat = BaseURL + "/api/chats/";

    public static String getChatHeadings = BaseURL + "api/chats/GetLastChats";

    public static String updateSeenChat = BaseURL + "api/chats/UpdateSeen/";

    public static String getAddresses = BaseURL + "api/Adress";

    public static String registerNewUser = BaseURL + "api/register";

    public static String getProfessions = BaseURL + "api/register";

    public static String getEditDetails = BaseURL + "api/UpdateUserProfile";

    public static String updateProfile = BaseURL + "api/UpdateUserProfile";

    public static String postFeedback = BaseURL + "api/AddFeedback/PostFeedback";

    public static String removeAddress = BaseURL + "api/UpdateUserProfile/DeleteAddress";

    public static String removeContact = BaseURL + "api/UpdateUserProfile/DeleteContact";

    public static String removeEmail = BaseURL + "api/UpdateUserProfile/DeleteEmail";

    public static String bookingDetails = BaseURL + "api/Booking/GetBookingDetails";

    public static String startBooking = BaseURL + "api/Booking/PostBooking";

    public static String updateBookings = BaseURL + "api/Booking/UpdateGetBooking";

    public static String getAllBookingRequests = BaseURL + "api/Booking/GetAllBookingRequests";

    public static String getAllNotifications = BaseURL + "api/Notification/GetAllNotifications";

    public static String cancelBooking = BaseURL + "api/Booking/DeleteBooking?bookingId=";

    public static String postBill = BaseURL + "api/Bill/PostBill/";

    public static String getAllFinalBookings = BaseURL + "api/Booking/GetAllBookings";

    public static String reportOffer = BaseURL + "api/Report/AddOfferReport";

    public static String reportProblem = BaseURL + "api/Report/AddProblemReport";

    public static String saveOffer = BaseURL + "api/SavePosts/SaveOffer/";

    public static String getUserSavedOffersId = BaseURL + "api/SavedPosts/SavedPostOfferId";

    public static String getAllSavedPostsDetails = BaseURL + "api/SavedPosts/GetSavedOffers/";

    public static String addUserToFav = BaseURL + "api/Favorites/AddToFavorites";

    public static String getUserFavId = BaseURL + "api/Favorites/GetFavUserId";

    public static String getUserFavDetails = BaseURL + "api/Favorites/GetUserFavorites";

    public static String getBookingHistory = BaseURL + "api/RecentBooking/GetRecentBookings";

    public static String deleteBookingHistory = BaseURL + "api/RecentBooking/DeleteRecentBooking?bookingId=";

    public static String verifyToResetPass = BaseURL + "api/ResetPassword/VerifyUser";

    public static String resetPassword = BaseURL + "api/ResetPassword/UpdatePassword";

    public static String getReplies = BaseURL + "api/AddFeedback/GetReply?feedbackId=";

    public static String addReplies = BaseURL + "api/AddFeedback/PostReply";

    public static String deleteOffer = BaseURL + "api/Offer/DeleteOffer?offerId=";

    public static String deleteProblem = BaseURL + "api/Problem/DeleteProblem?problemId=";

    public static String changePassword = BaseURL + "api/ChangePassword/ValidateUserPassword";

    public static String completeBooking = BaseURL + "api/Booking/BookingCompleted?bookingId=";

    public static String completeBookingByQR = BaseURL + "api/Booking/CompleteWithQrCode?customerToken=";
}
