package nassaty.dev;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Prince on 5/21/2017.
 */

public class ServerRequest extends Application {
    private static final String TAG = "login";

    private RequestQueue mQueue;
    private static ServerRequest instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized ServerRequest getInstance(){
        return instance;
    }

    public RequestQueue getQueue(){
        if (mQueue == null){
            mQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getQueue().add(req);
    }

    public void cancelPendingRequests(){
        if (mQueue != null){
            mQueue.cancelAll(TAG);
        }
    }

}
