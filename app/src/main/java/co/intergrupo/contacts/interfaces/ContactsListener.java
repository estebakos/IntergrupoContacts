package co.intergrupo.contacts.interfaces;

import java.util.List;

import co.intergrupo.contacts.entity.Contact;

/**
 * Created by TEBAN on 15/04/2016.
 */
public interface ContactsListener {
    void onContacts(List<Contact> contacts);

    void onContactsProblem();
}
