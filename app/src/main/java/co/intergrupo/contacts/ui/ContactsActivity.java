package co.intergrupo.contacts.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.entity.Contact;
import co.intergrupo.contacts.interfaces.ContactsListener;
import co.intergrupo.contacts.network.NetworkManager;
import co.intergrupo.contacts.utilities.AppContext;
import co.intergrupo.contacts.utilities.DataPreferences;

/**
 * Created by TEBAN on 15/04/2016.
 */
public class ContactsActivity extends AppCompatActivity implements ContactsListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rvContacts;
    private ContactsAdapter contactsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NetworkManager networkManager;
    private DataPreferences dataPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        rvContacts.setHasFixedSize(true);
        rvContacts.setLayoutManager(new LinearLayoutManager(AppContext.getContext()));
        networkManager = NetworkManager.getNManagerInstance();
        dataPreferences = new DataPreferences(this);
        networkManager.setContactsListener(this);
        networkManager.getContacts();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private boolean existOfflineContact(int id) {
        try {
            JSONArray contacts = new JSONArray(dataPreferences.getOfflineContacts());
            for (int i = 0; i < contacts.length(); i++) {
                Contact contact = new Gson().fromJson(contacts.getJSONObject(i).toString(), Contact.class);
                if (contact.getId() == id) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private Contact getOfflineContactById(int id) {
        Contact contact = new Contact();
        try {
            JSONArray contacts = new JSONArray(dataPreferences.getOfflineContacts());
            for (int i = 0; i < contacts.length(); i++) {
                Contact contactCycle = new Gson().fromJson(contacts.getJSONObject(i).toString(), Contact.class);
                if (contactCycle.getId() == id) {
                    contact = contactCycle;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contact;
    }

    @Override
    public void onContacts(List<Contact> contacts) {
        List<Contact> oldContacts = new ArrayList<>();
        List<Contact> newContacts = new ArrayList<>();
        try {
            for (Contact contact : contacts) {

                if (existOfflineContact(contact.getId())) {
                    newContacts.add(getOfflineContactById(contact.getId()));
                    oldContacts.add(contact);
                }
            }
            for (Contact old : oldContacts) {
                contacts.remove(old);
            }
            for (Contact newC : newContacts) {
                contacts.add(newC);
            }

            contactsAdapter = new ContactsAdapter(contacts);
            rvContacts.setAdapter(contactsAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onContactsProblem() {
        Toast.makeText(this, "Tenemos inconvenientes obteniendo la lista de contactos, desliza para reintentar", Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        networkManager.getContacts();
    }
}
