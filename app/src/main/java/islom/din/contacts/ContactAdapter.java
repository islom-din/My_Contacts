package islom.din.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<Contact> contacts;
    private OnItemClickListener localListener;
    private Context context;

    public ContactAdapter(ArrayList<Contact> contacts, OnItemClickListener listener, Context context) {
        this.contacts = contacts;
        this.context = context;
        localListener = listener;
    }

    public void filterList(ArrayList<Contact> newList) {
        this.contacts = newList;
        notifyDataSetChanged();
    }

    //==============================================================================================
    //                         Переопределяемые методы для адаптера
    //==============================================================================================
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.textView.setText(contacts.get(position).getName() + " " + contacts.get(position).getLastName());

        File file = new File(Environment.getExternalStorageDirectory(), contacts.get(position).getImg());
        if(!contacts.get(position).getImg().equals("")) {
            Glide.with(context)
                    .load(file)
                    .into(holder.imageView);
        }
        else if(contacts.get(position).getImg().equals("")){
            Glide.with(context)
                    .load(R.drawable.avatar)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    //==============================================================================================
    //                                ViewHolder
    //==============================================================================================
    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView textView;
        ImageView imageView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.profileImage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /**
             * Обработчик нажатия на элемент в списке. Обрабатывается только нажатие!!!
             * Вся остальная логика, которая следует после нажатия вынесена в MainActivity в
             * метод onItemClick.
             */
            localListener.onItemClick(getAdapterPosition());
        }
    }

    interface OnItemClickListener {
        /**
         * Интерфейс, связывающий ContactAdapter и MainActivity.
         * Класс MainActivity реализует этот интерфейс, а следовательно он переопределяется метод
         * onItemClick, который является обработчиком нажатия на элемент в списке.
         */
        void onItemClick(int position);
    }
}
