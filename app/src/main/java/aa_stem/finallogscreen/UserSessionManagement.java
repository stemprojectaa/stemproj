package aa_stem.finallogscreen;


import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSessionManagement {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "project_contents";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // MED variables (make variable public to access from outside)
    public static final String KEY_MEDICINE_NAME = "medicinename";
    public static final String KEY_DOSAGE_AMOUNT = "dosageamount";
    public static final String KEY_START_DATE = "start_date";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_HOME_PHONE = "homephone";
    public static final String KEY_CELL_PHONE = "cellphone";


    // Constructor
    public UserSessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }

    public void saveMedValues(String medical_name,String dose_amount,String start_date,String start_time){
        editor.putString(KEY_MEDICINE_NAME,medical_name);
        editor.putString(KEY_DOSAGE_AMOUNT,dose_amount);
        editor.putString(KEY_START_DATE,start_date);
        editor.putString(KEY_START_TIME,start_time);
        editor.commit();
    }

    public void savePhoneValues(String home_phone,String cell_phone){
        editor.putString(KEY_HOME_PHONE,home_phone);
        editor.putString(KEY_CELL_PHONE,cell_phone);
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data for Medical Values
     * */
    public HashMap<String, String> getMedicalDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> medDetails = new HashMap<String, String>();

        medDetails.put(KEY_MEDICINE_NAME, pref.getString(KEY_MEDICINE_NAME,"$$$$$"));
        medDetails.put(KEY_DOSAGE_AMOUNT, pref.getString(KEY_DOSAGE_AMOUNT, null));
        medDetails.put(KEY_START_DATE, pref.getString(KEY_START_DATE, null));
        medDetails.put(KEY_START_TIME, pref.getString(KEY_START_TIME, null));
        medDetails.put(KEY_HOME_PHONE, pref.getString(KEY_HOME_PHONE, null));
        medDetails.put(KEY_CELL_PHONE, pref.getString(KEY_CELL_PHONE, null));


        return medDetails;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String isValid() {
        return KEY_MEDICINE_NAME.toString();
    }

}
