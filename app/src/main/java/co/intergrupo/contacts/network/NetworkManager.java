package co.intergrupo.contacts.network;

import android.widget.Toast;

import java.util.List;

import co.intergrupo.contacts.entity.Contact;
import co.intergrupo.contacts.interfaces.ContactsDetailListener;
import co.intergrupo.contacts.interfaces.ContactsListener;
import co.intergrupo.contacts.interfaces.IWebService;
import co.intergrupo.contacts.interfaces.LoginListener;
import co.intergrupo.contacts.utilities.AppContext;

/**
 * Created by TEBAN on 14/04/2016.
 */
public class NetworkManager implements IWebService {

    private static NetworkManager NManagerInstance;
    private WebServiceManager webServiceManager;
    private LoginListener loginListener;
    private ContactsListener contactsListener;
    private ContactsDetailListener contactsDetailListener;


    public void setContactsDetailListener(ContactsDetailListener contactsDetailListener) {
        this.contactsDetailListener = contactsDetailListener;
    }

    public void setContactsListener(ContactsListener contactsListener) {
        this.contactsListener = contactsListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private NetworkManager() {
        webServiceManager = new WebServiceManager(AppContext.getContext(), this);
    }

    public static synchronized NetworkManager getNManagerInstance() {
        if (null == NManagerInstance) {
            synchronized (NetworkManager.class) {
                if (null == NManagerInstance) {
                    NManagerInstance = new NetworkManager();
                }
            }
        }
        return NManagerInstance;
    }

    public void login(String username, String password) {
        webServiceManager.login(username, password);
    }

    public void getContacts() {
        webServiceManager.getContacts();
    }

    public void getContactDetail(int id) {
        webServiceManager.getContactDetail(id);
    }

    @Override
    public void onLogin() {
        if (loginListener != null) {
            loginListener.onAuthenticate();
        }
    }

    @Override
    public void onLoginProblem() {
        if (loginListener != null) {
            loginListener.onAuthenticationError(0);
        }
    }

    @Override
    public void onContacts(List<Contact> contacts) {
        if (contactsListener != null) {
            contactsListener.onContacts(contacts);
        }
    }

    @Override
    public void onContactsProblem() {
        if (contactsListener != null) {
            contactsListener.onContactsProblem();
        }
    }

    @Override
    public void onInternetFail() {
        Toast.makeText(AppContext.getContext(), "Debes estar conectado a internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactDetail(Contact contact) {
        if (contactsDetailListener != null) {
            contactsDetailListener.onContactDetail(contact);
        }
    }

    @Override
    public void onContactDetailProblem() {
        if (contactsDetailListener != null) {
            contactsDetailListener.onContactDetailProblem();
        }
    }
}
