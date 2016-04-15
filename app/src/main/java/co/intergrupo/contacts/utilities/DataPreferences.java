package co.intergrupo.contacts.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by TEBAN on 18/03/2016.
 */
public class DataPreferences {


    /* Private Preferences */
    public static final String PREF_PRIVATE = "pref_private";

    public static final String PREF_USER_EMAIL = "pref_user_email";
    public static final String PREF_USER_PASSWORD = "pref_user_password";
    public static final String PREF_USER_AUTO_START = "pref_auto_start";
    public static final String PREF_USER_OFFLINE_CONTACTS = "pref_user_offline_contacts";

    private Context context;

    public DataPreferences(Context context) {
        this.context = context;
    }

    public String get(String sConfig,
                      String defaultValue) {
        return get(context, sConfig, defaultValue);
    }

    public void clear() {
        clear(context);
    }

    public static void clear(Context context) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        sharePreferences.edit().clear().commit();

        SharedPreferences privateSharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        privateSharePreferences.edit().clear().commit();

    }

    private static String get(Context context, String sConfig,
                              String defaultValue) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharePreferences.getString(sConfig, defaultValue);
    }

    private static String getPrivate(Context context, String sConfig, String defaultValue) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        return sharePreferences.getString(sConfig, defaultValue);
    }

    private Boolean get(String sConfig,
                        Boolean defaultValue) {
        return get(context, sConfig, defaultValue);
    }

    private static Boolean get(Context context, String sConfig,
                               Boolean defaultValue) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharePreferences.getBoolean(sConfig, defaultValue);
    }

    private static Boolean getPrivate(Context context, String sConfig,
                                      Boolean defaultValue) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        return sharePreferences.getBoolean(sConfig, defaultValue);
    }

    private Integer get(String sConfig,
                        Integer defaultValue) {
        return get(context, sConfig, defaultValue);
    }

    private static Integer get(Context context, String sConfig,
                               Integer defaultValue) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharePreferences.getInt(sConfig, defaultValue);
    }

    private static Integer getPrivate(Context context, String sConfig,
                                      Integer defaultValue) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        return sharePreferences.getInt(sConfig, defaultValue);
    }

    private void set(String sConfig, String value) {
        set(context, sConfig, value);
    }

    private static void set(Context context, String sConfig, String value) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putString(sConfig, value);
        spEditor.commit();
    }

    private static void setPrivate(Context context, String sConfig, String value) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putString(sConfig, value);
        spEditor.commit();
    }

    private void set(String sConfig, Boolean value) {
        set(context, sConfig, value);
    }

    private static void set(Context context, String sConfig, Boolean value) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putBoolean(sConfig, value);
        spEditor.commit();
    }

    private static void setPrivate(Context context, String sConfig, Boolean value) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putBoolean(sConfig, value);
        spEditor.commit();
    }

    private void set(String sConfig, Integer value) {
        set(context, sConfig, value);
    }

    private static void set(Context context, String sConfig, Integer value) {
        SharedPreferences sharePreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putInt(sConfig, value);
        spEditor.commit();
    }

    private static void setPrivate(Context context, String sConfig, Integer value) {
        SharedPreferences sharePreferences = context.getSharedPreferences(PREF_PRIVATE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharePreferences.edit();
        spEditor.putInt(sConfig, value);
        spEditor.commit();
    }

    public void storageEmail(String value) {
        if (value != null && !value.equals("")) {
            set(context, PREF_USER_EMAIL, value);
        } else {
            set(context, PREF_USER_EMAIL, "");
        }
    }

    public void storageOfflineContacts(String value) {
        if (value != null && !value.equals("")) {
            set(context, PREF_USER_OFFLINE_CONTACTS, value);
        } else {
            set(context, PREF_USER_OFFLINE_CONTACTS, "");
        }
    }

    public void storageAutoStart(Boolean value) {
        set(context, PREF_USER_AUTO_START, value);
    }

    public void storagePassword(String value) {
        if (value != null && !value.equals("")) {
            set(context, PREF_USER_PASSWORD, value);
        } else {
            set(context, PREF_USER_PASSWORD, "");
        }
    }

    public String getPassword() {
        return get(context, PREF_USER_PASSWORD, "");
    }

    public String getOfflineContacts() {
        return get(context, PREF_USER_OFFLINE_CONTACTS, "");
    }

    public String getEmail() {
        return get(context, PREF_USER_EMAIL, "");
    }

    public Boolean isAutoStart() {
        return get(context, PREF_USER_AUTO_START, false);
    }

}
