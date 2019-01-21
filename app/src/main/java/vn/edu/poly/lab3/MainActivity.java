package vn.edu.poly.lab3;

import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnShowContact;
    private Button btnShowCallLogs;
    private Button btnShowMediaStore;
    private Button btnShowAppSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowContact = findViewById(R.id.btnShowContact);
        btnShowCallLogs = findViewById(R.id.btnShowCallLogs);
        btnShowMediaStore = findViewById(R.id.btnShowMediaStore);
        btnShowAppSettings = findViewById(R.id.btnShowAppSettings);


        btnShowContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,danhBa.class);
                startActivity(intent);

            }
        });
        btnShowCallLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accessTheCallLog();
            }
        });
        btnShowMediaStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessMediaStore();
            }
        });
        btnShowAppSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,bookmark.class);
                startActivity(intent);
            }
        });



    }

    public void accessTheCallLog()
    {
        String [] projection=new String[]{
                CallLog.Calls.DATE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION
        };
        Cursor c=getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                CallLog.Calls.DURATION+"<?",new String[]{"30"},
                CallLog.Calls.DATE +" Asc");
        c.moveToFirst();
        String s="";
        while(c.isAfterLast()==false){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        c.close();
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
    public void accessMediaStore()
    {
        String []projection={
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader loader;
        loader = new CursorLoader
                (this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);
        Cursor c=loader.loadInBackground();
        c.moveToFirst();
        String s="";
        while(!c.isAfterLast()){
            for(int i=0;i<c.getColumnCount();i++){
                s+=c.getString(i)+" - ";
            }
            s+="\n";
            c.moveToNext();
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        c.close();
    }


}
