package co.intergrupo.contacts.ui;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.intergrupo.contacts.R;
import co.intergrupo.contacts.entity.Contact;
import co.intergrupo.contacts.utilities.AppContext;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private static List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_contacts, viewGroup, false);

        ContactViewHolder contactViewHolder = new ContactViewHolder(itemLayoutView);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, final int i) {

        Contact contact = contacts.get(i);

        Picasso.with(AppContext.getContext()).load(contact.getProfileImage()).error(R.drawable.contacts_icon).placeholder(R.drawable.contacts_icon).fit().centerCrop().into(contactViewHolder.ivProfilePicture);

        switch (contact.getState()) {
            case 1:
                Picasso.with(AppContext.getContext()).load(R.drawable.green_circle).into(contactViewHolder.ivState);
                break;
            case 2:
                Picasso.with(AppContext.getContext()).load(R.drawable.blue_circle).into(contactViewHolder.ivState);
                break;
            case 3:
                Picasso.with(AppContext.getContext()).load(R.drawable.red_circle).into(contactViewHolder.ivState);
                break;
            default:
                break;
        }

        contactViewHolder.tvPhone.setText(contact.getPhoneNumber());
        contactViewHolder.tvName.setText(String.format("%s %s", contact.getName(), contact.getLastName()));
        contactViewHolder.tvAlias.setText(contact.getAlias());
        contactViewHolder.cvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ContactDetailActivity.class);
                intent.putExtra("Id", contacts.get(i).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvAlias, tvPhone;
        public ImageView ivProfilePicture, ivState;
        public CardView cvContacts;

        public ContactViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvAlias = (TextView) itemLayoutView.findViewById(R.id.tvAlias);
            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            tvPhone = (TextView) itemLayoutView.findViewById(R.id.tvPhone);
            ivProfilePicture = (ImageView) itemLayoutView.findViewById(R.id.ivProfilePicture);
            ivState = (ImageView) itemLayoutView.findViewById(R.id.ivState);
            cvContacts = (CardView) itemLayoutView.findViewById(R.id.cvContacts);
        }

    }
}
