package co.intergrupo.contacts.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.interfaces.LoginListener;


public class LoginActivity extends AppCompatActivity implements LoginListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onAuthenticate() {

    }

    @Override
    public void onAuthenticationError(int code) {

    }
}

