package co.intergrupo.contacts.interfaces;

import java.util.List;

import co.intergrupo.contacts.entity.Contact;

/**
 * Created by TEBAN on 15/04/2016.
 */
public interface ContactsDetailListener {
    void onContactDetail(Contact contact);

    void onContactDetailProblem();
}
