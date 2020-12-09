package islom.din.contacts;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutContactActivity extends AppCompatActivity {
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_contact);

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

    }
}
