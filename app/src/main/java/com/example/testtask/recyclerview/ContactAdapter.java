package com.example.testtask.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.testtask.R;
import com.example.testtask.model.Contact;

import java.util.List;
//адаптер контейнера
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private OnItemClickListener mItemClickListener;
    private OnDeleteListener onDeleteListener;

    private LayoutInflater inflater;
    private List<Contact> contacts;
    //обработка нажатия на контакт
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;

    }
    //удаление записей из контейнера
    public interface OnDeleteListener {
        void onDelete(Contact contact);
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(context);
        notifyDataSetChanged();
    }
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        holder.tvContact.setText(contacts.get(position).getName());
        holder.tvNumber.setText(contacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvContact;
        TextView tvNumber;
        Button btnDelete;

        ViewHolder(View view){
            super(view);
            tvContact = view.findViewById(R.id.tvContact);
            tvNumber = view.findViewById(R.id.tvNum);

            btnDelete = view.findViewById(R.id.btnDelete);

            tvContact.setOnClickListener(this);
            tvNumber.setOnClickListener(this);

            btnDelete.setOnClickListener(v ->
            {
                onDeleteListener.onDelete(contacts.get(getAdapterPosition()));
                contacts.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                for(int i = 0; i < contacts.size(); i++)
                {
                    if(contacts.get(i).getId() != i)
                    {
                        contacts.get(i).setId(i);
                    }
                }
            });
        }





        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
