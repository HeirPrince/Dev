package nassaty.dev.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nassaty.dev.Helper.SessionManager;
import nassaty.dev.Helper.SqliteHandler;
import nassaty.dev.Helper.account_constants;
import nassaty.dev.R;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText inputPhone;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SqliteHandler db;
    private RequestQueue mQueue;
    private TextView signuplink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputPhone = (EditText)findViewById(R.id.phn);
        inputPassword = (EditText)findViewById(R.id.pwd);
        signuplink = (TextView)findViewById(R.id.slink);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        mQueue = Volley.newRequestQueue(this);

        //sqlite db handler
        db = new SqliteHandler(getApplicationContext());

        //sesison manager
        session = new SessionManager(getApplicationContext());

        //check if user is already logged in or not
        if (session.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUp.class));
                finish();
            }
        });


    }

    public void login(View view) {
        String phone = inputPhone.getText().toString();
        String password = inputPassword.getText().toString();

        //check empty data
        if (!phone.isEmpty() && !password.isEmpty()){
            checkLogin(phone,password);
        }else {
            Toast.makeText(this, "Please enter credentials", Toast.LENGTH_SHORT).show();
        }
    }

    //verify login details
    private void checkLogin(final String phone, final String password) {
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, account_constants.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login response"+response.toString());
                hideDialog();

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    boolean error = jsonObj.getBoolean("error");

                    if (!error){
                        session.setLogin(true);

                        String uuid = jsonObj.getString("uuid");
                        JSONObject user = jsonObj.getJSONObject("user");
                        String name = user.getString("name");
                        String phone = user.getString("phone");
                        String created_at = user.getString("created_at");

                        db.addUser(uuid, name, phone, created_at);

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

                    }else{
                        String errorMsg = jsonObj.getString("error_msg");
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Application error", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Login error");
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("phone",phone);
                params.put("password",password);
                return params;
            }
        };

        mQueue.add(strReq);


    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showDialog() {
        if (pDialog.isShowing())
            pDialog.show();
    }
}
