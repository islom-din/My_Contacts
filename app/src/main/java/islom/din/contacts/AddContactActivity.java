package islom.din.contacts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    // Extra-данные из AboutContactActivity
    private int id;
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

    private boolean toEdit = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);
        contactName = findViewById(R.id.name);
        contactLastName = findViewById(R.id.lastName);
        contactPhone = findViewById(R.id.phone);
        contactEmail = findViewById(R.id.email);
        buttonOk = findViewById(R.id.buttonOk);
        getExtraData();
        setEnabledButtons();
    }

    private void setEnabledButtons() {
        if(getIntent().getBooleanExtra("edit", false))
            toEdit = true;
    }

    private void getExtraData() {
        contactName.setHint(getIntent().getStringExtra("name"));
        contactLastName.setHint(getIntent().getStringExtra("lastName"));
        contactPhone.setHint(getIntent().getStringExtra("phone"));
        contactEmail.setHint(getIntent().getStringExtra("email"));

    }


}
