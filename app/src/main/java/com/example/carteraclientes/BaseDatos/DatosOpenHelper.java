package com.example.carteraclientes.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatosOpenHelper extends SQLiteOpenHelper {
    public DatosOpenHelper(Context context) {
        super(context, "DATOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append("CREATE TABLE IF  NOT EXISTS CLIENTE (");
        builder.append("NOMBRE VARCHAR(250), ");
        builder.append("DIRECCION VARCHAR(250), ");
        builder.append("EMAIL VARCHAR(200), ");
        builder.append("TELEFONO VARCHAR(20)) ");

        db.execSQL(builder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
