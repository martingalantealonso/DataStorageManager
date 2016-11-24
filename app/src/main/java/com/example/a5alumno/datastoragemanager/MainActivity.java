package com.example.a5alumno.datastoragemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView hello_TxtView;
    private EditText update_EdtTxt;
    private SharedPreferences mSharedPreferences;
    private static final String MY_SHARED_PREFERENCES = "MySharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.hello_TxtView = (TextView) this.findViewById(R.id.textViewHello);
        this.update_EdtTxt = (EditText) this.findViewById(R.id.edtTextUpdate);
        final Button ok_Btn = (Button) this.findViewById(R.id.btnOk);
        ok_Btn.setOnClickListener(this);
        final Button internalStorage_Btn = (Button) this.findViewById(R.id.btnInternalStorage);
        internalStorage_Btn.setOnClickListener(this);
        final Button externalStorage_Btn = (Button) this.findViewById(R.id.btnExternalStorage);
        externalStorage_Btn.setOnClickListener(this);
        final Button getFiles_Btn = (Button) this.findViewById(R.id.btnGetFiles);
        getFiles_Btn.setOnClickListener(this);
        final Button createDB_Btn = (Button) this.findViewById(R.id.btnDataBase);
        createDB_Btn.setOnClickListener(this);


        //Get SharedPreferences
        mSharedPreferences = this.getSharedPreferences(MainActivity.MY_SHARED_PREFERENCES, MODE_PRIVATE);
        hello_TxtView.setText(mSharedPreferences.getString("myFinalString", "String not found"));

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnOk) {
            hello_TxtView.setText(update_EdtTxt.getText().toString());

            //region Esto se deberia de hacer en el onStop :)
            mSharedPreferences = this.getSharedPreferences(MainActivity.MY_SHARED_PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putString("myFinalString", this.hello_TxtView.getText().toString());
            mEditor.apply();
            //endregion
        } else if (view.getId() == R.id.btnInternalStorage) {
            try {
                FileOutputStream mFileOutputStream=this.openFileOutput("internalStorageFile.txt",MODE_PRIVATE);
                mFileOutputStream.write((update_EdtTxt.getText().toString()+"\n").getBytes());
                mFileOutputStream.close();
                Snackbar.make(view, "Fichero guardado :)", Snackbar.LENGTH_SHORT).show();
                Log.i("InfoUtil","Fichero guardado en memoria interna: "+ getFilesDir());
            } catch (IOException ex) {
                Snackbar.make(view, "Error al guardar el fichero :(", Snackbar.LENGTH_SHORT).show();
            }
        }else if(view.getId()==R.id.btnExternalStorage){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                File mFileOutputStream=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"filecillos/");

                if (!mFileOutputStream.mkdirs()) {
                    Log.e("InfoUtil", "Directory not created");
                }
            }else{
                Snackbar.make(view, "Directorio no disponible :(", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
