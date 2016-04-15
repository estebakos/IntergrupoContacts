package co.intergrupo.contacts.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.interfaces.LoginListener;
import co.intergrupo.contacts.network.NetworkManager;
import co.intergrupo.contacts.utilities.AppContext;
import co.intergrupo.contacts.utilities.DataPreferences;

public class SplashActivity extends AppCompatActivity implements LoginListener {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    private Timer timer;
    private TimerTask task;
    private NetworkManager networkManager;
    private DataPreferences dataPreferences;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideToolbars();
        setContentView(R.layout.activity_splash);

        AppContext.setActivity(this);
        AppContext.setContext(this);
        networkManager = NetworkManager.getNManagerInstance();
        networkManager.setLoginListener(this);
        dataPreferences = new DataPreferences(this);

        task = new TimerTask() {
            @Override
            public void run() {
                openLogin();
            }
        };

        if (dataPreferences.isAutoStart() && dataPreferences.getEmail() != null && !dataPreferences.getEmail().equals("") && dataPreferences.getPassword() != null && !dataPreferences.getPassword().equals("")) {
            networkManager.login(dataPreferences.getEmail(), dataPreferences.getPassword());
        } else {
            timer = new Timer();
            timer.schedule(task, SPLASH_SCREEN_DELAY);
        }
    }

    private void hideToolbars() {
        if (Build.VERSION.SDK_INT >= 19) {
            //si es mayor o igual a API 19 kitkat ocultamos las barras UI del sistema
            getWindow().getDecorView().setSystemUiVisibility(
                    256 //SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | 512 //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | 1024 //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | 2 //SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | 4 //SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | 4096 //SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    256 //SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | 512 //SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | 1024 //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | 2 //SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | 4 //SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            );
        }
    }

    public void openLogin() {
        Intent mainIntent = new Intent().setClass(SplashActivity.this, LoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onAuthenticate() {
        Intent mainIntent = new Intent().setClass(SplashActivity.this, ContactsActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onAuthenticationError(int code) {
        Toast.makeText(this, "Credenciales inválidas, debes loguearte nuevamente", Toast.LENGTH_LONG).show();
        timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
