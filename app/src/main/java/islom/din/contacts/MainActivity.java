package islom.din.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnItemClickListener {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    ContactAdapter adapter;

    // Массив с данными о контактах
    Contact[] contacts = new Contact[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });

        // Заполняем массив
        setContactsArray();

        // Инициализируем recyclerView список
        initRecyclerView();
    }

    private void setContactsArray() {
        contacts[0] = new Contact(1, "Islom", "Nuridinov");
        contacts[1] = new Contact(2, "Alex", "Cold");
        contacts[2] = new Contact(3, "Diana", "Sparrow");
        contacts[3] = new Contact(4, "Dean", "Winchester");
        contacts[4] = new Contact(5, "Sam", "Winchester");
        contacts[5] = new Contact(6, "Erric", "Banas");
        contacts[6] = new Contact(7, "Dominic", "Torretto");
        contacts[7] = new Contact(8, "Aiden", "Pierce");
        contacts[8] = new Contact(9, "Elliot", "Alderson");
        contacts[9] = new Contact(10, "Michel", "Rodriges");
        contacts[10] = new Contact(11, "Rachel", "Wood");
        contacts[11] = new Contact(12, "Eleonora", "Dean");
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        adapter = new ContactAdapter(contacts, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Some text", Toast.LENGTH_SHORT).show();
    }
}