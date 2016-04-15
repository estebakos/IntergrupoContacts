package co.intergrupo.contacts.interfaces;

import java.util.List;

import co.intergrupo.contacts.entity.Contact;

/**
 * Created by TEBAN on 14/04/2016.
 */
public interface IWebService {
    void onLogin();

    void onLoginProblem();

    void onContacts(List<Contact> contact);

    void onContactsProblem();

    void onInternetFail();

    void onContactDetail(Contact contact);

    void onContactDetailProblem();
}
