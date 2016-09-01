package com.mmsoft.mutlu.navis;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 import android.widget.Toast;

/**
 * Created by mut on 04.08.2015.
 */
public class TakeList extends Activity {
    private Database locats;

    private String[] SELECT = {"id", "address", "latitude", "longitude"};

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // KayitGetir();
        // KayitGoster(cursor);
    }

    public Cursor KayitGetir(Cursor c) {
        SQLiteDatabase db = locats.getReadableDatabase();
        db.execSQL("Select * from location");
        Cursor cursor = db.query("location", SELECT, null, null, null, null, null);
        // String[] columns = new String[]{"id" , "address", "latitude", "longitude"};
        // Cursor cursor = locats.(location, columns, null, null, null, null,"id"+" DESC");
        Log.d("TAG", SELECT[1]);
        return cursor;

    }

    public void KayitGoster(Cursor cursor) {
     /*   StringBuilder builder = new StringBuilder("Kayitlar:n");

        while (cursor.moveToNext()) {

            long id = cursor.getLong(cursor.getColumnIndex("id"));
            String add = cursor.getString((cursor.getColumnIndex("isim")));
            String lat = cursor.getString((cursor.getColumnIndex("soyad")));
            String lon = cursor.getString((cursor.getColumnIndex("midterm")));


            builder.append(id).append(" add: ");
            builder.append(add).append(" lat: ");
            builder.append(lat).append("lon : ");
            builder.append(lon).append("\n");

        }
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText(builder);
    }*/

        while (cursor.moveToNext()) {
            Integer str1=0;
            str1 = str1 + cursor.getInt(1);
            String str2 ="";
            str2 = str2 + cursor.getString(2);
            Toast.makeText(this, str1 + str2, Toast.LENGTH_LONG).show();
        }
    }
}