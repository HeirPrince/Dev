package nassaty.dev.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import nassaty.dev.R;

public class SignUp extends AppCompatActivity {

    private TextInputEditText name,phone,password;
    private Button btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
