package islom.din.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myContactsDB"; // Название базы данных

    // Таблицы в БД
    public static final String TABLE_CONTACTS = "contactsTable"; // Название таблицы
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String IMG = "img";

    public DBHelper(Context context) {
        super(context, NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTS + " ("
                + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, "
                + LAST_NAME + " TEXT, "
                + PHONE + " TEXT, "
                + EMAIL + " TEXT, "
                + IMG + " TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(sqLiteDatabase);
    }
}
