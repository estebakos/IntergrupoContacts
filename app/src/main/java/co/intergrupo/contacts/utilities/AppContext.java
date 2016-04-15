package co.intergrupo.contacts.utilities;

import android.app.Activity;
import android.content.Context;

/**
 * Created by TEKUSPC4 on 31/12/15.
 */
public class AppContext {

    private static Context context;

    private static Activity activity;

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        AppContext.activity = activity;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppContext.context = context;
    }

}
