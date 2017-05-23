package nassaty.dev.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Prince on 5/23/2017.
 */

public class SessionManager {

    private static final String TAG = "sessions";
    SharedPreferences pref;

    SharedPreferences.Editor mEditor;
    Context mContext;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Login";

    private static final String IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = pref.edit();
        this.mContext = context;
    }

    public void setLogin(boolean isLoggedIn) {
        mEditor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        mEditor.commit();

        Log.d(TAG, "*******user login session modified********");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

}
