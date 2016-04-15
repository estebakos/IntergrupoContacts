package co.intergrupo.contacts.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.entity.Contact;
import co.intergrupo.contacts.interfaces.ContactsDetailListener;
import co.intergrupo.contacts.network.NetworkManager;
import co.intergrupo.contacts.utilities.DataPreferences;

/**
 * Created by TEBAN on 15/04/2016.
 */
public class ContactDetailActivity extends AppCompatActivity implements ContactsDetailListener {

    private EditText etName, etLastName, etPhoneNumber, etCountry, etCity, etAlias, etImageUrl;
    private ImageView ivProfilePicture;
    private Spinner spStates;
    private Button btnSave;

    private NetworkManager networkManager;
    private DataPreferences dataPreferences;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        networkManager = NetworkManager.getNManagerInstance();
        networkManager.setContactsDetailListener(this);
        dataPreferences = new DataPreferences(this);

        etAlias = (EditText) findViewById(R.id.etAlias);
        etCity = (EditText) findViewById(R.id.etCity);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etImageUrl = (EditText) findViewById(R.id.etImageUrl);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhone);

        spStates = (Spinner) findViewById(R.id.spStates);
        List<String> list = Arrays.asList(getResources().getStringArray(R.array.states));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStates.setAdapter(dataAdapter);

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContactData();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("Id");
            if (!dataPreferences.getOfflineContacts().equals("") && existOfflineContact(id)) {
                putContactData(getOfflineContactById(id));
            } else {
                networkManager.getContactDetail(id);
            }
        }
    }

    private void putContactData(Contact contact) {
        if(contact != null)
        {
            etPhoneNumber.setText(contact.getPhoneNumber());
            etLastName.setText(contact.getLastName());
            etName.setText(contact.getName());

            etImageUrl.setText(contact.getProfileImage());
            Picasso.with(this).load(contact.getProfileImage()).placeholder(R.drawable.contacts_icon).error(R.drawable.contacts_icon).fit().centerCrop().into(ivProfilePicture);

            etAlias.setText(contact.getAlias());
            etCity.setText(contact.getCity());
            etCountry.setText(contact.getCountry());
            spStates.setSelection(contact.getState() - 1);
        }
    }

    private void saveContactData()
    {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setAlias(etAlias.getText().toString());
        contact.setCity(etCity.getText().toString());
        contact.setCountry(etCountry.getText().toString());
        contact.setLastName(etLastName.getText().toString());
        contact.setName(etName.getText().toString());
        contact.setPhoneNumber(etPhoneNumber.getText().toString());
        contact.setProfileImage(etImageUrl.getText().toString());
        Picasso.with(this).load(etImageUrl.getText().toString()).placeholder(R.drawable.contacts_icon).error(R.drawable.contacts_icon).fit().centerCrop().into(ivProfilePicture);
        contact.setState(spStates.getSelectedItemPosition() + 1);

        if(dataPreferences.getOfflineContacts().equals(""))
        {
            List<Contact> contacts = new ArrayList<>();
            contacts.add(contact);
            dataPreferences.storageOfflineContacts(new Gson().toJson(contacts));
        }
        else {
            try {
                List<Contact> contactsAdd = new ArrayList<>();
                JSONArray contacts = new JSONArray(dataPreferences.getOfflineContacts());
                JSONArray newContacts = new JSONArray();
                for (int i = 0; i < contacts.length(); i++) {
                    Contact contactCycle = new Gson().fromJson(contacts.getJSONObject(i).toString(), Contact.class);
                    if (contactCycle.getId() != id) {
                        contactsAdd.add(contactCycle);
                    }
                }
                contactsAdd.add(contact);
                dataPreferences.storageOfflineContacts(new Gson().toJson(contactsAdd));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onContactDetail(Contact contact) {
        putContactData(contact);
    }

    @Override
    public void onContactDetailProblem() {
        Toast.makeText(this, "Ocurrió un problema obteniendo la información del contacto", Toast.LENGTH_SHORT).show();
    }
}
