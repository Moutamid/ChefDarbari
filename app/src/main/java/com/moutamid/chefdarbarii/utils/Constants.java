package com.moutamid.chefdarbarii.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {
    public static final String AFFILIATE = "AFFILIATE";
    public static final String CHEF = "CHEF";
    public static final String PARAMS = "PARAMS";

    public static final String IS_LOGGED_IN = "isloggedin";
    public static final String NULL = "null";
    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";
    public static final String USERS = "users";
    public static final String FILTER_USER = "User";
    public static final String FILTER_VIDEOS = "Videos";
    public static final String CURRENT_USER_MODEL = "currentusermodel";
    public static final String ADMIN = "admin";
    public static final String ADMIN_BOOKINGS = "ADMIN_BOOKINGS";
    public static final String VIDEO_APPROVAL_REQUEST = "video_approval_requests";
    public static final String IS_APPROVED = "is_approved";
    public static final String PUBLIC_POSTS = "public_posts";
    public static final String CHATS = "chats";
    public static final String CONVERSATIONS = "conversations";
    public static final String SEPARATOR = "LrDEBoLokW-5mhaT3ys";
    public static final String CHAT_MODEL = "chat_model";
    public static final String DEFAULT_PROFILE_URL = "https://firebasestorage.googleapis.com/v0/b/sweet-nutrition.appspot.com/o/Frame%201888.png?alt=media&token=a9609231-27b3-4b53-854b-957455b6613d";
    public static final String SPONSORED_ACCOUNTS = "sponsored_accounts";
    public static final String FOLLOWING = "following";
    public static final String FOLLOWERS = "followers";
    public static final String FOLLOWERS_LIST = "followers_list";
    public static final String FOLLOWING_LIST = "following_list";
    public static final String MY_PASSWORD = "my_password";
    public static final String CONTACT_REQUESTS = "contact_requests";
    public static final String CURRENT_CONTACT_REQUEST = "current_contact_request";
    public static final String REPORTED_MESSAGES = "reported_messages";
    public static final String CHOSEN_CONTACTS_LIST = "chosen_contacts_list";
    public static final String CURRENT_POST_MODEL = "current_post_model";
    public static final String IS_CONTACT_CHECKED = "IS_CONTACT_CHECKED";
    public static final String IS_FOLLOWER_CHECKED = "IS_FOLLOWER_CHECKED";
    public static final String NEW_PARTY_BOOKINGS = "NEW_PARTY_BOOKINGS";
    public static final String CURRENT_JOB_MODEL = "CURRENT_JOB_MODEL";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String CURRENT_CHEF_MODEL = "CURRENT_CHEF_MODEL";
    public static final String CURRENT_AFFILIATE_MODEL = "CURRENT_AFFILIATE_MODEL";
    public static final String ACCEPTED_JOBS = "ACCEPTED_JOBS";
    public static final String COMPLETED_JOBS = "COMPLETED_JOBS";
    public static final String ADMIN_NOTIFICATIONS = "admin_notifications";
    public static final String AFFILIATE_NOTIFICATIONS = "affiliate_notifications";
    public static final String CHEF_NOTIFICATIONS = "chef_notifications";
    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";
    public static final String KEY = "KEY";
    public static final String AFFILIATE_LAST_BOOKING_ID = "AFFILIATE_LAST_BOOKING_ID";
    public static final String TITLE = "TITLE";
    public static final String BODY = "BODY";
    public static final String PAUSE_STATUS = "PAUSE_STATUS";
//    public static final String BOLD_TYPE = "text/html; charset=utf-8";

    public static FirebaseAuth auth() {
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference databaseReference() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("ChefDarbariApp");
        db.keepSynced(true);
        return db;
    }
}
