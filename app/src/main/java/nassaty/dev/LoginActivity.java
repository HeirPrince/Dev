package nassaty.dev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "login";
    private TextView phone,password;
    private RequestQueue request;
    String url = "http://api.androidhive.info/volley/person_object.json";
    //http://192.168.40.207/spinner/php/login.php

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        phone = (TextView)findViewById(R.id.phone);
        password = (TextView)findViewById(R.id.password);
        request = Volley.newRequestQueue(LoginActivity.this);

    }

    public void initRequest(String url){

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject obj = response.getJSONObject("phone");
                    String home = obj.getString("home");
                    String mobile = obj.getString("mobile");
                    Toast.makeText(LoginActivity.this, name+" "+email+" "+home+" "+mobile, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Failed with error : "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObj);

    }

    public void login(View view) {
        initRequest(url);
    }
}
