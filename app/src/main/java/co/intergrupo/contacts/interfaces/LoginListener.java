package co.intergrupo.contacts.interfaces;

/**
 * Created by TEBAN on 13/04/2016.
 */
public interface LoginListener {
    void onAuthenticate();

    void onAuthenticationError(int code);
}
