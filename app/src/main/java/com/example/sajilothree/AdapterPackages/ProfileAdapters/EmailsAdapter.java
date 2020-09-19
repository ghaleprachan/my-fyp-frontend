package com.example.sajilothree.AdapterPackages.ProfileAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sajilothree.ModelsPackage.ProfileModelPackage.ProfileDetails.Email;
import com.example.sajilothree.R;

import java.util.List;

public class EmailsAdapter extends RecyclerView.Adapter<EmailsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Email> emails;

    public EmailsAdapter(Context mContext, List<Email> emails) {
        this.mContext = mContext;
        this.emails = emails;
    }

    @NonNull
    @Override
    public EmailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.profile_emails_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.email.setText(emails.get(position).getEmail1());
        holder.aboutEmail.setText(emails.get(position).getEmailType());
    }

    @Override
    public int getItemCount() {
        return emails.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView email, aboutEmail;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.title);
            aboutEmail = itemView.findViewById(R.id.aboutTitle);
        }
    }
}
