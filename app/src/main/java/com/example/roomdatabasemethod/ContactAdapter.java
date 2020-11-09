package com.example.roomdatabasemethod;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {

    private List<Contact> contacts=new ArrayList<>();

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {

        Contact currContact=contacts.get(position);
        holder.name.setText(currContact.getName());
        holder.number.setText(currContact.getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts){
        this.contacts=contacts;
        notifyDataSetChanged();
    }

    class ContactHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView number;

        public ContactHolder(View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.contactName);
            number=itemView.findViewById(R.id.contactNumber);
        }
    }
}
