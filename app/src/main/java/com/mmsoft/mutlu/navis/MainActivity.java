package com.mmsoft.mutlu.navis;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

public class MainActivity extends Activity {
    //Button btnGoLocation;
    Button btnShowLocation;
    Button btnSaveLocation;
    Button btnDelLocation;
    ListView liste;
    // GPSTracker class
    GPSTracker gps;
    private Database dtbs;
    TakeList listele = new TakeList();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dtbs = new Database(this);

        liste = (ListView) findViewById(R.id.listView);
        final  ArrayList li=new ArrayList<String>();
        String[] SELECT = {"id", "address", "latitude","longitude"};
        SQLiteDatabase db = dtbs.getReadableDatabase();
        final ContentValues datas = new ContentValues();
        final Cursor cursor = db.query("location", SELECT, null, null, null, null, null);
        final  String[] dizilat = new String[100000];
        final  String[] dizilon = new String[100000];
        int i=0;
        if (cursor.moveToFirst()) {
            do {
                li.add("id:"+cursor.getString(0) + "         " + cursor.getString(1) + "\nLatitude: " + cursor.getString(2) + "\nLongitude: " + cursor.getString(3));
                dizilat[i] = cursor.getString(2);
                dizilon[i] = cursor.getString(3);
                i++;
            } while (cursor.moveToNext());

        }

        db.close();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, li);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        liste.setAdapter(adapter);

        btnShowLocation = (Button) findViewById(R.id.button);
        btnSaveLocation = (Button) findViewById(R.id.button2);
        //btnGoLocation = (Button) findViewById(R.id.button3);
        btnDelLocation = (Button) findViewById(R.id.button4);
        final EditText enlem = (EditText) findViewById(R.id.editText2);
        final EditText boylam = (EditText) findViewById(R.id.editText3);
        final EditText address = (EditText) findViewById(R.id.editText);
        final EditText del_id = (EditText)findViewById(R.id.editText4);


        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Uri link = Uri.parse("http://maps.google.co.uk/maps?q=" + dizilat[position] + "," + dizilon[position] + "");

                Intent tara = new Intent(Intent.ACTION_DEFAULT, link);

                startActivity(tara);

                Context context = getApplicationContext();
                CharSequence text = "Going to ...\n" + dizilat[position] + "\n" + dizilon[position];
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                /*
                Uri link = Uri.parse("http://maps.google.co.uk/maps?q=baris%20mahallesi%20dr.%20zeki%20acar%20caddesi%201826/2");

                Intent tara = new Intent(Intent.ACTION_DEFAULT, link);

                startActivity(tara);
                */
            }


        });

        btnShowLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // GPS' nin aktif olması kontrolü.
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    //pop-up bildirim
                    //  Toast.makeText(getApplicationContext(), "Konumunuz - \nEnlem: " + latitude + "\nBoylam: " + longitude, Toast.LENGTH_LONG).show();
                    enlem.setText("" + latitude);
                    boylam.setText("" + longitude);
                } else {
                    // Konuma ulaşılamıyor..!
                    // GPS veya Network kullanılabilir değil..!
                    // Ayarları aktifleştirmesi için kullanıcı ile iletişime geç.
                    gps.showSettingsAlert();
                }

            }
        });


        btnSaveLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //   double latitude = gps.getLatitude();
                // double longitude = gps.getLongitude();
                if (enlem.getText().toString() == "" ) {

                    Toast.makeText(getApplicationContext(), " fill in the blanks",Toast.LENGTH_LONG).show();

                }
                else {


                    String add = address.getText().toString();
                    SQLiteDatabase db = dtbs.getWritableDatabase();
                    ContentValues datas = new ContentValues();
                    datas.put("address", add);
                    datas.put("latitude", enlem.getText().toString());
                    datas.put("longitude", boylam.getText().toString());

                    db.insertOrThrow("location", null, datas);

                    dtbs.close();
                    finish();
                    startActivity(getIntent());
                    Context context = getApplicationContext();
                    CharSequence text = "" + add + " successfully saved!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });
/*
        btnGoLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

               String senlem = enlem.getText().toString();
                String sboylam = boylam.getText().toString();
                Uri link = Uri.parse("http://maps.google.co.uk/maps?q=" + senlem + "," + sboylam + "");
                // Intent ile yönlendirme yapiliyo
                Intent tara = new Intent(Intent.ACTION_DEFAULT, link);
                // Olusturdugumuz intent sayesinde activityi baslatiyoruz
                startActivity(tara);
            }
        });
*/
        btnDelLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SQLiteDatabase db = dtbs.getWritableDatabase();
                db.execSQL("Delete from location Where id = " + del_id.getText().toString() + "");
                //db.execSQL("Delete from location WHERE(id > 30 AND id < 5431)"); //BELLİ ARALIKTAKİ BİLGİLERİ SİLME
                db.close();
                Toast.makeText(getApplicationContext(), "" + del_id.getText().toString() + "    successfully deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());

            }
        });
    }

}