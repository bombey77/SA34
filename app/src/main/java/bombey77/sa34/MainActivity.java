package bombey77.sa34;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName, etEmail, etId;
    Button btnAdd, btnRead, btnClear, btnUpdate, btnDelete;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etId = (EditText) findViewById(R.id.etId);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etId.getText().toString();

        switch (v.getId()) {
            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_EMAIL, email);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                Log.d("myLog", "Row created");
                break;
            case R.id.btnRead:

                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int indexId = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int indexName = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int indexEmail = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                    do {
                        Log.d("myLog", "ID = " + cursor.getInt(indexId) + ", Name = " + cursor.getString(indexName) + ", Email = " + cursor.getString(indexEmail));
                    } while (cursor.moveToNext());
                } else {
                    Log.d("myLog", "0 rows");
                }
                cursor.close();
                break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                Log.d("myLog", "Database cleared");
                break;
            case R.id.btnUpdate:
                if (id.equalsIgnoreCase("")) {
                    break;
                } contentValues.put(DBHelper.KEY_NAME, name);
                 contentValues.put(DBHelper.KEY_EMAIL, email);
                int updCount = database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[]{id});
                Log.d("myLog", "Update rows counts " + updCount);
                break;
            case R.id.btnDelete:
                if (id.equalsIgnoreCase("")) {
                    break;
                } int delCount = database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "= " + id, null);
                Log.d("myLog", "Deleted rows counts " + delCount);
                break;
        }
        database.close();
    }
}
