package islom.din.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Contact[] localContacts;
    private OnItemClickListener localListener;

    public ContactAdapter(Contact[] contacts, OnItemClickListener listener) {
        localContacts = new Contact[contacts.length];
        localListener = listener;

        for(int i=0; i<contacts.length; i++) {
            localContacts[i] = contacts[i];
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.textView.setText(localContacts[position].getName() + " " + localContacts[position].getLastName());
    }

    @Override
    public int getItemCount() {
        return localContacts.length;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView textView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            localListener.onItemClick(getAdapterPosition());
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }
}
