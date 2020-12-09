package islom.din.contacts;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import islom.din.contacts.database.DBHelper;

public class AddContactActivity extends AppCompatActivity {

    // Заполняемые данные пользователем
    private String name;
    private String lastName;
    private String phone;
    private String email;

    // Виджеты layout
    private EditText contactName;
    private EditText contactLastName;
    private EditText contactPhone;
    private EditText contactEmail;
    private Button buttonOk;

    // База данных
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        dbHelper = new DBHelper(this);
        contactName = findViewById(R.id.name);
        contactLastName = findViewById(R.id.lastName);
        contactPhone = findViewById(R.id.phone);
        contactEmail = findViewById(R.id.email);
        buttonOk = findViewById(R.id.buttonOk);
        setListenerToOkButton();
    }

    private void setListenerToOkButton() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = contactName.getText().toString();
                lastName = contactLastName.getText().toString();
                phone = contactPhone.getText().toString();
                email = contactEmail.getText().toString();
                insertNewContact(name, lastName, phone, email);
            }
        });
    }

    private void insertNewContact(String name, String lastName, String phone, String email) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.NAME, name);
        contentValues.put(dbHelper.LAST_NAME, lastName);
        contentValues.put(dbHelper.PHONE, phone);
        contentValues.put(dbHelper.EMAIL, email);

        database.insert(dbHelper.TABLE_CONTACTS, null, contentValues);
        database.close();
        Toast.makeText(AddContactActivity.this, "Контакт добавлен", Toast.LENGTH_SHORT).show();
        finish();
    }


}
