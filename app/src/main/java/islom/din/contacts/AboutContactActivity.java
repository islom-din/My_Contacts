package islom.din.contacts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * В момент запуска данной activity, в методе onCreate последовательно выполняются три метода,
 * которые соответственно отвечают за следующие действия
 *  1) Получение данных из MainActivity --> метод getExtraData
 *  2) Установка обработчиков нажатий --> метод setListeners
 *  3) Показ данных о пользователе --> метод showContactsInfo
 */

public class AboutContactActivity extends AppCompatActivity {

    // Extra-данные из MainActivity
    private int id;
    private String name;
    private String lastName;
    private String phone;
    private String email;

    // Основные виджеты в layout
    private CircleImageView profileImage;
    private TextView contactName;
    private TextView contactPhone;
    private TextView contactEmail;

    // Виджеты с обработчиками
    private ImageView backArrow;
    private ImageView editContact;
    private ImageView deleteContact;
    private ImageView callIcon;
    private ImageView smsIcon;
    private ImageView emailIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_contact);

        getExtraData(); // 1) Получим данные из MainActivity

        setListeners(); // 2) Установим обработчики нажатий на элементы в верхнем toolbar

        showContactsInfo(); // 3) Дальше вызываем этот метод, чтобы показать данные о пользователе
    }

    private void getExtraData() {
        /**
         * Получение данных, отправленных из MainActivity. Этот метод вызывается перед тем,
         * как показывать данные о пользователе
         */
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        lastName = getIntent().getStringExtra("lastName");
        phone = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("email");
    }

    private void setListeners() {
        /**
         * Инициализация обработчиков
         */
        backArrow = findViewById(R.id.iconArrow);
        editContact = findViewById(R.id.iconEdit);
        deleteContact = findViewById(R.id.iconDelete);
        callIcon = findViewById(R.id.iconCall);
        smsIcon = findViewById(R.id.iconSms);
        emailIcon = findViewById(R.id.iconEmail);

        backArrow.setOnClickListener(new View.OnClickListener() {
            // При нажатии на стрелку покинуть текущую активити
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editContact.setOnClickListener(new View.OnClickListener() {
            // Редактировать контакт
            @Override
            public void onClick(View view) {
                RelativeLayout displayContainer = findViewById(R.id.displayContainer);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        AboutContactActivity.this, R.style.BottomSheetDialogTheme);
                View dialogView = LayoutInflater.from(AboutContactActivity.this)
                        .inflate(R.layout.bottom_sheet_display, displayContainer);
                bottomSheetDialog.setContentView(dialogView);
                bottomSheetDialog.show();
            }
        });

        deleteContact.setOnClickListener(new View.OnClickListener() {
            // Удалить контакт
            @Override
            public void onClick(View view) {

            }
        });

        callIcon.setOnClickListener(new View.OnClickListener() {
            // Позвонить по номеру (Вызов системного приложения звонков)
            // Используем для этого неявный intent
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        smsIcon.setOnClickListener(new View.OnClickListener() {
            // Написать sms контакту (Вызов системного приложения sms)
            // Используем для этого неявный intent
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null)));
            }
        });

        emailIcon.setOnClickListener(new View.OnClickListener() {
            // Написать письмо (Вызов системного приложения почты)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void showContactsInfo() {
        /**
         * Показать данные о пользователе
         */
        profileImage = findViewById(R.id.profileImage);
        contactName = findViewById(R.id.contactName);
        contactPhone = findViewById(R.id.contactPhone);
        contactEmail = findViewById(R.id.contactEmail);

        contactName.setText(name + " " + lastName);
        contactPhone.setText(phone);
        contactEmail.setText(email);
    }
}
