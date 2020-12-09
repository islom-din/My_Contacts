package islom.din.contacts;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutContactActivity extends AppCompatActivity {

    // Extra-данные из MainActivity
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private String email;

    // Виджеты в layout
    private CircleImageView profileImage;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_contact);
        profileImage = findViewById(R.id.profileImage);
        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        contactEmail = findViewById(R.id.contactEmail);

        getExtraData(); // Вызываем этот метод в первую очередь
        showContactsData(); // Дальше вызываем этот метод, чтобы показатьданные о пользователе
    }

    private void getExtraData() {
        /**
         * Получение данных, отправленных из MainActivity. Этот метод вызывается в первую очередь.
         */
        id = getIntent().getIntExtra("id", 1);
        name = getIntent().getStringExtra("name");
        lastName = getIntent().getStringExtra("lastName");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
    }

    private void showContactsData() {
        contactName.setText(name + " " + lastName);
        contactPhone.setText(phone);
        contactEmail.setText(email);
    }
}
