package com.example.sajilothree.AdapterPackages.ProfileAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails.Contact;
import com.example.sajilothree.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Contact> contacts;

    public ContactsAdapter(Context mContext, List<Contact> contacts) {
        this.mContext = mContext;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.profile_contact_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contact.setLinksClickable(true);
        holder.contact.setText(contacts.get(position).getContactNumber());
        holder.aboutContact.setText(contacts.get(position).getContactType());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView contact, aboutContact;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.title);
            aboutContact = itemView.findViewById(R.id.aboutTitle);
        }
    }
}
