package islom.din.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import islom.din.contacts.database.DBHelper;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnItemClickListener {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    ContactAdapter adapter;

    // Список с данными о контактах
    ArrayList<Contact> contacts;

    // База данных
    DBHelper dbHelper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        database = dbHelper.getReadableDatabase();

        // Заполняем массив
        setContactsArray();

        // Инициализируем recyclerView список
        initRecyclerView();

    }

    private void setContactsArray() {
        contacts = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(dbHelper.TABLE_CONTACTS, null, null, null, null, null, null);
        Contact contact;

        if(cursor.moveToFirst()) {

            int idIndex = cursor.getColumnIndex(dbHelper.ID);
            int nameIndex = cursor.getColumnIndex(dbHelper.NAME);
            int lastNameIndex = cursor.getColumnIndex(dbHelper.LAST_NAME);
            int phoneIndex = cursor.getColumnIndex(dbHelper.PHONE);
            int emailIndex = cursor.getColumnIndex(dbHelper.EMAIL);

            do {
                contact = new Contact(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(lastNameIndex),
                        cursor.getString(phoneIndex),
                        cursor.getString(emailIndex)
                );
                contacts.add(contact);
            } while (cursor.moveToNext());

        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        adapter = new ContactAdapter(contacts, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(int position) {
        /**
         * Этот метод из интерфейса, который реализует наш MainActivity. Обратите внимание, что сюда
         * передаётся position в качестве параметра. position - это позиция нажатого элемента. Теперь
         * мы можем обратиться к индексу в arrayList, который соответствует параметру position, а
         * затем вытащить объект по этому индексу и передать эти свойства через intent в другую активити.
         */

        Intent intent = new Intent(MainActivity.this, AboutContactActivity.class);
        intent.putExtra("id", contacts.get(position).getId());
        intent.putExtra("name", contacts.get(position).getName());
        intent.putExtra("lastName", contacts.get(position).getLastName());
        intent.putExtra("phone", contacts.get(position).getPhone());
        intent.putExtra("email", contacts.get(position).getEmail());
        startActivity(intent);
    }
}