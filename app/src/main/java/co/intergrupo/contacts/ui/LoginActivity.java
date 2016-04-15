package co.intergrupo.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.interfaces.LoginListener;
import co.intergrupo.contacts.network.NetworkManager;
import co.intergrupo.contacts.utilities.AppContext;
import co.intergrupo.contacts.utilities.DataPreferences;


public class LoginActivity extends AppCompatActivity implements LoginListener {

    private NetworkManager networkManager;
    private DataPreferences dataPreferences;

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private CheckBox chkRememberPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppContext.setContext(this);
        AppContext.setActivity(this);

        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        chkRememberPassword = (CheckBox) findViewById(R.id.chkRememberPassword);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        networkManager = NetworkManager.getNManagerInstance();
        networkManager.setLoginListener(this);

        dataPreferences = new DataPreferences(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals("") || etEmail.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Debe ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    networkManager.login(etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });
    }

    @Override
    public void onAuthenticate() {
        dataPreferences.storageAutoStart(chkRememberPassword.isChecked());
        dataPreferences.storageEmail(etEmail.getText().toString());
        dataPreferences.storagePassword(etPassword.getText().toString());
        Intent intent = new Intent().setClass(LoginActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthenticationError(int code) {
        Toast.makeText(this, "Usuario o password inválido", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}

