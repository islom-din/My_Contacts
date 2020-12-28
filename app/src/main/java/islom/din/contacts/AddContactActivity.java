package islom.din.contacts;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private CircleImageView imageView;

    // База данных
    DBHelper dbHelper;

    private Bitmap bitMap = null;
    private String globalPath = "";

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
        imageView = findViewById(R.id.profileImage);
        setListenerToImage();
        setListenerToOkButton();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permission, 1);
            }
            else {

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(AddContactActivity.this, "Permisstion enabled", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private final int CAMERA_REQUEST = 0;

    private void setListenerToImage() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AddContactActivity.this)
                        .setTitle("Загрузить фото")
                        .setMessage("Как вы хотите загрузить фото профиля")
                        .setNegativeButton("Сфотографировать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_REQUEST);
                            }
                        })
                        .setPositiveButton("Выбрать из галереи", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , CAMERA_REQUEST);//one can be replaced with any action code
                            }
                        })
                        .create()
                        .show();
            }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (requestCode == 0) {
                    Bundle extras = data.getExtras();
                    Bitmap bmp = (Bitmap) extras.get("data");
                    // here you will get the image as bitmap
                    Glide.with(AddContactActivity.this)
                            .load(bmp)
                            .centerCrop()
                            .into(imageView);
                    bitMap = bmp;



                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private void saveFile(Bitmap bitmap) {
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        File path = Environment.getExternalStorageDirectory();
        File dir = new File(path + "/DCIM");
        dir.mkdir();
        String imagename = time + ".PNG";
        globalPath = "/DCIM/" + imagename;

        File file = new File(dir, imagename);
        OutputStream out;

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadFile() {

    }

    private void setListenerToOkButton() {
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = contactName.getText().toString();
                lastName = contactLastName.getText().toString();
                phone = contactPhone.getText().toString();
                email = contactEmail.getText().toString();
                if(!name.isEmpty() && !phone.isEmpty()) {
                    name.trim();
                    lastName.trim();
                    email.trim();
                    if(bitMap != null) {
                        saveFile(bitMap);
                        insertNewContact(name, lastName, phone, email, globalPath);
                    }
                    else insertNewContact(name, lastName, phone, email, "");
                } else {
                    Toast.makeText(AddContactActivity.this, "Заполните обязательные поля!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void insertNewContact(String name, String lastName, String phone, String email, String globalPath) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(dbHelper.NAME, name);
        contentValues.put(dbHelper.LAST_NAME, lastName);
        contentValues.put(dbHelper.PHONE, phone);
        contentValues.put(dbHelper.EMAIL, email);
        contentValues.put(dbHelper.IMG, globalPath);

        database.insert(dbHelper.TABLE_CONTACTS, null, contentValues);
        database.close();
        Toast.makeText(AddContactActivity.this, "Контакт добавлен", Toast.LENGTH_SHORT).show();
        finish();
    }
}
